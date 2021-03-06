/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.weixin.entity.WeixinMsgBase;
import com.thinkgem.jeesite.modules.weixin.service.WeixinMsgBaseService;
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
import com.thinkgem.jeesite.modules.weixin.entity.WeixinMsgText;
import com.thinkgem.jeesite.modules.weixin.service.WeixinMsgTextService;

import java.util.List;

/**
 * 微信文本消息Controller
 * @author HAILOU
 * @version 2016-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/weixinMsgText")
public class WeixinMsgTextController extends BaseController {

	@Autowired
	private WeixinMsgTextService weixinMsgTextService;
	@Autowired
	private WeixinMsgBaseService weixinMsgBaseService;

	@ModelAttribute
	public WeixinMsgText get(@RequestParam(required=false) String id) {
		WeixinMsgText entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weixinMsgTextService.get(id);
		}
		if (entity == null){
			entity = new WeixinMsgText();
		}
		return entity;
	}
	
	@RequiresPermissions("weixin:weixinMsgText:view")
	@RequestMapping(value = {"list", ""})
	public String list(WeixinMsgText weixinMsgText, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeixinMsgText> page = weixinMsgTextService.findPage(new Page<WeixinMsgText>(request, response), weixinMsgText); 
		model.addAttribute("page", page);
		return "modules/weixin/weixinMsgTextList";
	}

	@RequiresPermissions("weixin:weixinMsgText:view")
	@RequestMapping(value = "form")
	public String form(WeixinMsgText weixinMsgText, Model model) {

		List<WeixinMsgBase> list = weixinMsgBaseService.findList(new WeixinMsgBase());
		model.addAttribute("baseList", list);

		model.addAttribute("weixinMsgText", weixinMsgText);
		return "modules/weixin/weixinMsgTextForm";
	}

	@RequiresPermissions("weixin:weixinMsgText:edit")
	@RequestMapping(value = "save")
	public String save(WeixinMsgText weixinMsgText, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weixinMsgText)){
			return form(weixinMsgText, model);
		}
		weixinMsgTextService.save(weixinMsgText);
		addMessage(redirectAttributes, "保存微信文本消息成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/weixinMsgText/?repage";
	}
	
	@RequiresPermissions("weixin:weixinMsgText:edit")
	@RequestMapping(value = "delete")
	public String delete(WeixinMsgText weixinMsgText, RedirectAttributes redirectAttributes) {
		weixinMsgTextService.delete(weixinMsgText);
		addMessage(redirectAttributes, "删除微信文本消息成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/weixinMsgText/?repage";
	}

}