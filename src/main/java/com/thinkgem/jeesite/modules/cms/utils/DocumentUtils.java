package com.thinkgem.jeesite.modules.cms.utils;

import com.thinkgem.jeesite.modules.cms.entity.Article;
import org.apache.lucene.document.*;

/**
 * @项目名称：lucene
 * @类名称：DocumentUtils
 * @类描述：文章实体类和Document的转换工具
 * @创建人：HAILOU
 * @创建时间：2016年8月31日 上午10:15:22
 * @version 1.0.0
 */
public class DocumentUtils {
    public static Document article2Document(Article article) {
        Document doc = new Document();
        doc.add(new Field("id", article.getId(), StringField.TYPE_STORED));
        doc.add(new Field("title", article.getTitle(), TextField.TYPE_STORED));
        doc.add(new Field("categoryID", article.getCategory().getId(), TextField.TYPE_STORED));
        doc.add(new Field("description", article.getDescription(), TextField.TYPE_STORED));
        //doc.add(new Field("content", article.getArticleData().getContent(), TextField.TYPE_STORED));
        return doc;
    }

    public static Article document2Ariticle(Document doc) {
        Article article = new Article();
        article.setId(doc.get("id"));
        article.setTitle(doc.get("title"));
        article.getCategory().setId(doc.get("categoryID"));
        article.setDescription(doc.get("description"));
        //article.getArticleData().setContent(doc.get("content"));
        return article;
    }
}
