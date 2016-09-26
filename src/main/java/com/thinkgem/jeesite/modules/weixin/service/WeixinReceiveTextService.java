/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.weixin.entity.WeixinReceiveText;
import com.thinkgem.jeesite.modules.weixin.dao.WeixinReceiveTextDao;

/**
 * 微信接收信息Service
 * @author HAILOU
 * @version 2016-09-21
 */
@Service
@Transactional(readOnly = true)
public class WeixinReceiveTextService extends CrudService<WeixinReceiveTextDao, WeixinReceiveText> {

	public WeixinReceiveText get(String id) {
		return super.get(id);
	}
	
	public List<WeixinReceiveText> findList(WeixinReceiveText weixinReceiveText) {
		return super.findList(weixinReceiveText);
	}
	
	public Page<WeixinReceiveText> findPage(Page<WeixinReceiveText> page, WeixinReceiveText weixinReceiveText) {
		return super.findPage(page, weixinReceiveText);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinReceiveText weixinReceiveText) {
		super.save(weixinReceiveText);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinReceiveText weixinReceiveText) {
		super.delete(weixinReceiveText);
	}
	
}