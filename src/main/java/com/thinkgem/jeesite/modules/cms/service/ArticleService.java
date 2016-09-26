/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.service;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cms.dao.ArticleDao;
import com.thinkgem.jeesite.modules.cms.dao.ArticleDataDao;
import com.thinkgem.jeesite.modules.cms.dao.CategoryDao;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.utils.DocumentUtils;
import com.thinkgem.jeesite.modules.cms.utils.LuceneUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;

import org.apache.lucene.search.highlight.Scorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;

import java.util.List;

/**
 * 文章Service
 * @author HAILOU
 * @version 2016-05-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends CrudService<ArticleDao, Article> {

	@Autowired
	private ArticleDataDao articleDataDao;
	@Autowired
	private CategoryDao categoryDao;
	
	@Transactional(readOnly = false)
	public Page<Article> findPage(Page<Article> page, Article article, boolean isDataScopeFilter) {
		// 更新过期的权重，间隔为“6”个小时
		Date updateExpiredWeightDate =  (Date)CacheUtils.get("updateExpiredWeightDateByArticle");
		if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null 
				&& updateExpiredWeightDate.getTime() < new Date().getTime())){
			dao.updateExpiredWeight(article);
			CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
		}
//		DetachedCriteria dc = dao.createDetachedCriteria();
//		dc.createAlias("category", "category");
//		dc.createAlias("category.site", "category.site");
		if (article.getCategory()!=null && StringUtils.isNotBlank(article.getCategory().getId()) && !Category.isRoot(article.getCategory().getId())){
			Category category = categoryDao.get(article.getCategory().getId());
			if (category==null){
				category = new Category();
			}
			category.setParentIds(category.getId());
			category.setSite(category.getSite());
			article.setCategory(category);
		}
		else{
			article.setCategory(new Category());
		}
//		if (StringUtils.isBlank(page.getOrderBy())){
//			page.setOrderBy("a.weight,a.update_date desc");
//		}
//		return dao.find(page, dc);
	//	article.getSqlMap().put("dsf", dataScopeFilter(article.getCurrentUser(), "o", "u"));
		return super.findPage(page, article);
		
	}

	@Transactional(readOnly = false)
	public void save(Article article) {
		if (article.getArticleData().getContent()!=null){
			article.getArticleData().setContent(StringEscapeUtils.unescapeHtml4(
					article.getArticleData().getContent()));
		}
		// 如果没有审核权限，则将当前内容改为待审核状态
		if (!UserUtils.getSubject().isPermitted("cms:article:audit")){
			article.setDelFlag(Article.DEL_FLAG_AUDIT);
		}
		// 如果栏目不需要审核，则将该内容设为发布状态
		if (article.getCategory()!=null&&StringUtils.isNotBlank(article.getCategory().getId())){
			Category category = categoryDao.get(article.getCategory().getId());
			if (!Global.YES.equals(category.getIsAudit())){
				article.setDelFlag(Article.DEL_FLAG_NORMAL);
			}
		}
		article.setUpdateBy(UserUtils.getUser());
		article.setUpdateDate(new Date());
        if (StringUtils.isNotBlank(article.getViewConfig())){
            article.setViewConfig(StringEscapeUtils.unescapeHtml4(article.getViewConfig()));
        }

        //创建索引
		createIndex(article);

        ArticleData articleData = new ArticleData();;
		if (StringUtils.isBlank(article.getId())){
			article.preInsert();
			articleData = article.getArticleData();
			articleData.setId(article.getId());
			dao.insert(article);
			articleDataDao.insert(articleData);
		}else{
			article.preUpdate();
			articleData = article.getArticleData();
			articleData.setId(article.getId());
			dao.update(article);
			articleDataDao.update(article.getArticleData());
		}

    }
	
	@Transactional(readOnly = false)
	public void delete(Article article, Boolean isRe) {
//		dao.updateDelFlag(id, isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
		// 使用下面方法，以便更新索引。
		//Article article = dao.get(id);
		//article.setDelFlag(isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
		//dao.insert(article);
		super.delete(article);

		/*************** 删除索引 ***************************/
		IndexWriter indexWriter = null;
		try {

			/*String[] fields = {"id"};
			QueryParser parser = new MultiFieldQueryParser(fields, LuceneUtils.getAnalyzer());
			Query query = parser.parse(article.getId());*/

			Term term = new Term("id", article.getId());
			Query query = new TermQuery(term);
			IndexWriterConfig config = new IndexWriterConfig(LuceneUtils.getAnalyzer());
			indexWriter = new IndexWriter(LuceneUtils.getDirectory(), config);

			indexWriter.deleteDocuments(query);// 删除含有指定term的所有文档

		} catch (Exception e) {
			logger.error("ArticleService.save error", e);
		} finally {
			LuceneUtils.closeIndexWriter(indexWriter);
		}
	}
	
	/**
	 * 通过编号获取内容标题
	 * @return new Object[]{栏目Id,文章Id,文章标题}
	 */
	public List<Object[]> findByIds(String ids) {
		if(ids == null){
			return new ArrayList<Object[]>();
		}
		List<Object[]> list = Lists.newArrayList();
		String[] idss = StringUtils.split(ids,",");
		Article e = null;
		for(int i=0;(idss.length-i)>0;i++){
			e = dao.get(idss[i]);
			list.add(new Object[]{e.getCategory().getId(),e.getId(),StringUtils.abbr(e.getTitle(),50)});
		}
		return list;
	}
	
	/**
	 * 点击数加一
	 */
	@Transactional(readOnly = false)
	public void updateHitsAddOne(String id) {
		dao.updateHitsAddOne(id);
	}
	
	/**
	 * 更新索引
	 */
	public int createIndex(Article article){
		//dao.createIndex();
		int flag = 0;
		Document doc = DocumentUtils.article2Document(article);
		IndexWriter indexWriter = null;
		try {
			IndexWriterConfig config = new IndexWriterConfig(LuceneUtils.getAnalyzer());
			indexWriter = new IndexWriter(LuceneUtils.getDirectory(), config);

			if (StringUtils.isBlank(article.getId())){
				/*************** 增加到索引 ***************************/
				indexWriter.addDocument(doc);
			}else{
				/*************** 修改索引 ***************************/
				Term term = new Term("id", article.getId());
				indexWriter.updateDocument(term, doc);// 先删除，后创建。
			}
		} catch (Exception e) {
			logger.error("ArticleService.save error", e);
		} finally {
			LuceneUtils.closeIndexWriter(indexWriter);
		}
		return flag;
	}
	
	/**
	 * 全文检索
	 */
	public Page<Article> search(Page<Article> page, String q, String categoryId, String beginDate, String endDate){
		
		// 设置查询条件
		//BooleanQuery query = dao.getFullTextQuery(q, "title","keywords","description","articleData.content");

		// 设置过滤条件
		/*List<BooleanClause> bcList = Lists.newArrayList();

		bcList.add(new BooleanClause(new TermQuery(new Term(Article.FIELD_DEL_FLAG, Article.DEL_FLAG_NORMAL)), BooleanClause.Occur.MUST));
		if (StringUtils.isNotBlank(categoryId)){
			bcList.add(new BooleanClause(new TermQuery(new Term("category.ids", categoryId)), BooleanClause.Occur.MUST));
		}

		if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {

            bcList.add(new BooleanClause(new TermRangeQuery("updateDate",
                                                            beginDate.replaceAll("-", ""),
                                                            endDate.replaceAll("-", ""),
                                                            true, true),
                                                            BooleanClause.Occur.MUST));
		}
		
		BooleanQuery queryFilter = dao.getFullTextQuery((BooleanClause[])bcList.toArray(new BooleanClause[bcList.size()]));
*/
		//System.out.println(queryFilter);
		
		// 设置排序（默认相识度排序）
		//FIXME 暂时不提供lucene检索
		Sort sort = null;//new Sort(new SortField("updateDate", SortField.DOC, true));
		// 全文检索
		//dao.search(page, query, queryFilter, sort);
		// 关键字高亮
		//dao.keywordsHighlight(query, page.getList(), 30, "title");
		//dao.keywordsHighlight(query, page.getList(), 130, "description","articleData.content");

        /***************************************************************************************************/
        List<Article> list = new ArrayList<Article>();
        try {
			//1、创建Directory
            DirectoryReader ireader = DirectoryReader.open(LuceneUtils.getDirectory());
            // 2、第二步，创建搜索器
            IndexSearcher isearcher = new IndexSearcher(ireader);
            // 3、第三步，类似SQL，进行关键字查询
            String[] fields = {"title","description"};
            QueryParser parser = new MultiFieldQueryParser(fields, LuceneUtils.getAnalyzer());
            Query query = parser.parse(q);
            TopDocs topDocs = isearcher.search(query,10000);// 第二个参数，指定最多返回前n条结果
            ScoreDoc[] hits = topDocs.scoreDocs;// 总记录数
            // 高亮
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			Scorer source = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter, source);

			// 摘要
          	//Fragmenter fragmenter = new SimpleFragmenter(hits.length);
          	//highlighter.setTextFragmenter(fragmenter);

			//查询起始记录位置
			int begin = page.getPageSize() * (page.getPageNo() - 1);
			//查询终止记录位置
			int end = Math.min(begin + page.getPageSize(), hits.length);

            for (int i=begin;i<end;i++) {
                Document hitDoc = isearcher.doc(hits[i].doc);
                Article article = DocumentUtils.document2Ariticle(hitDoc);

                String title = highlighter.getBestFragment(LuceneUtils.getAnalyzer(), "title", hitDoc.get("title"));
				String description = highlighter.getBestFragment(LuceneUtils.getAnalyzer(), "description", hitDoc.get("description"));
				//String content = highlighter.getBestFragment(LuceneUtils.getAnalyzer(), "content", hitDoc.get("content"));
                if (title != null) {
                    article.setTitle(title);
                }
				if (description != null) {
					article.setDescription(description);
				}
				/*if (content != null) {
					article.getArticleData().setContent(content);
				}*/
				list.add(article);
            }
            ireader.close();

            page.setCount(hits.length);
            page.setList(list);
        } catch (Exception e) {
            logger.error("ArticleService.search error", e);
        }
        return page;
	}
	
}
