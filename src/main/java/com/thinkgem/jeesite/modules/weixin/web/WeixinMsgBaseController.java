/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.weixin.entity.WeixinAccount;
import com.thinkgem.jeesite.modules.weixin.service.WeixinAccountService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.weixin.entity.WeixinMsgBase;
import com.thinkgem.jeesite.modules.weixin.service.WeixinMsgBaseService;

import java.util.List;

/**
 * 微信关键词生Controller
 * @author HAILOU
 * @version 2016-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/weixinMsgBase")
public class WeixinMsgBaseController extends BaseController {

	@Autowired
	private WeixinMsgBaseService weixinMsgBaseService;
	@Autowired
	private WeixinAccountService weixinAccountService;

	@ModelAttribute
	public WeixinMsgBase get(@RequestParam(required=false) String id) {
		WeixinMsgBase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weixinMsgBaseService.get(id);
		}
		if (entity == null){
			entity = new WeixinMsgBase();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:weixinMsgBase:view")
	@RequestMapping(value = {"list", ""})
	public String list(WeixinMsgBase weixinMsgBase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeixinMsgBase> page = weixinMsgBaseService.findPage(new Page<WeixinMsgBase>(request, response), weixinMsgBase); 
		model.addAttribute("page", page);
		return "modules/weixin/weixinMsgBaseList";
	}

	@RequiresPermissions("weixin:weixinMsgBase:view")
	@RequestMapping(value = "form")
	public String form(WeixinMsgBase weixinMsgBase, Model model) {
		List<WeixinAccount> list = weixinAccountService.findList(new WeixinAccount());
		model.addAttribute("weixinAccountList", list);

		model.addAttribute("weixinMsgBase", weixinMsgBase);
		return "modules/weixin/weixinMsgBaseForm";
	}

	@RequiresPermissions("weixin:weixinMsgBase:edit")
	@RequestMapping(value = "save")
	public String save(WeixinMsgBase weixinMsgBase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weixinMsgBase)){
			return form(weixinMsgBase, model);
		}
		weixinMsgBaseService.save(weixinMsgBase);
		addMessage(redirectAttributes, "保存微信关键词生成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/weixinMsgBase/?repage";
	}
	
	@RequiresPermissions("weixin:weixinMsgBase:edit")
	@RequestMapping(value = "delete")
	public String delete(WeixinMsgBase weixinMsgBase, RedirectAttributes redirectAttributes) {
		weixinMsgBaseService.delete(weixinMsgBase);
		addMessage(redirectAttributes, "删除微信关键词生成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/weixinMsgBase/?repage";
	}

}