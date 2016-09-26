/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.weixin.entity.WeixinMsgText;
import com.thinkgem.jeesite.modules.weixin.dao.WeixinMsgTextDao;

/**
 * 微信文本消息Service
 * @author HAILOU
 * @version 2016-09-19
 */
@Service
@Transactional(readOnly = true)
public class WeixinMsgTextService extends CrudService<WeixinMsgTextDao, WeixinMsgText> {

	public WeixinMsgText get(String id) {
		return super.get(id);
	}
	
	public List<WeixinMsgText> findList(WeixinMsgText weixinMsgText) {
		return super.findList(weixinMsgText);
	}
	
	public Page<WeixinMsgText> findPage(Page<WeixinMsgText> page, WeixinMsgText weixinMsgText) {
		return super.findPage(page, weixinMsgText);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinMsgText weixinMsgText) {
		super.save(weixinMsgText);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinMsgText weixinMsgText) {
		super.delete(weixinMsgText);
	}
	
}