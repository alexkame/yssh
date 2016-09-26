/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web;


import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Comment;
import com.thinkgem.jeesite.modules.cms.entity.Guestbook;
import com.thinkgem.jeesite.modules.cms.entity.Site;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.cms.service.GuestbookService;
import com.thinkgem.jeesite.modules.cms.utils.CmsUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.h2.command.ddl.CreateIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网站搜索Controller
 * @author hailou
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/search")
public class SearchController extends BaseController{
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private GuestbookService guestbookService;


	@RequestMapping(value = {"list", ""})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/cms/search";
	}
	@RequestMapping(value = "createAticleIndex")
	@ResponseBody
	public Map<String, Object> createAticleIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> returnMap = new HashMap<>();
		List<Article> list = articleService.findList(new Article());
		for(int i=0;i<list.size();i++){
			System.out.println("第"+i+"条索引创建将要创建，标题为："+list.get(i).getTitle());
			articleService.createIndex(list.get(i));
			System.out.println("第"+i+"条索引已创建，标题为："+list.get(i).getTitle());
		}
		returnMap.put("size", list.size());

		return returnMap;
	}
}
