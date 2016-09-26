/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.weixin.entity.WeixinMsgBase;
import com.thinkgem.jeesite.modules.weixin.dao.WeixinMsgBaseDao;

/**
 * 微信关键词生Service
 * @author HAILOU
 * @version 2016-09-19
 */
@Service
@Transactional(readOnly = true)
public class WeixinMsgBaseService extends CrudService<WeixinMsgBaseDao, WeixinMsgBase> {

	public WeixinMsgBase get(String id) {
		return super.get(id);
	}
	
	public List<WeixinMsgBase> findList(WeixinMsgBase weixinMsgBase) {
		return super.findList(weixinMsgBase);
	}
	
	public Page<WeixinMsgBase> findPage(Page<WeixinMsgBase> page, WeixinMsgBase weixinMsgBase) {
		return super.findPage(page, weixinMsgBase);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinMsgBase weixinMsgBase) {
		super.save(weixinMsgBase);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinMsgBase weixinMsgBase) {
		super.delete(weixinMsgBase);
	}
	
}