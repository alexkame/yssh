/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.weixin.entity.WeixinMessage;
import com.thinkgem.jeesite.modules.weixin.dao.WeixinMessageDao;

/**
 * 微信推送信息Service
 * @author HAILOU
 * @version 2016-09-20
 */
@Service
@Transactional(readOnly = true)
public class WeixinMessageService extends CrudService<WeixinMessageDao, WeixinMessage> {

	public WeixinMessage get(String id) {
		return super.get(id);
	}
	
	public List<WeixinMessage> findList(WeixinMessage weixinMessage) {
		return super.findList(weixinMessage);
	}
	
	public Page<WeixinMessage> findPage(Page<WeixinMessage> page, WeixinMessage weixinMessage) {
		return super.findPage(page, weixinMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinMessage weixinMessage) {
		super.save(weixinMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinMessage weixinMessage) {
		super.delete(weixinMessage);
	}
	
}