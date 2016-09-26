/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.weixin.entity.WeixinFans;
import com.thinkgem.jeesite.modules.weixin.dao.WeixinFansDao;

/**
 * 微信粉丝Service
 * @author HAILOU
 * @version 2016-09-20
 */
@Service
@Transactional(readOnly = true)
public class WeixinFansService extends CrudService<WeixinFansDao, WeixinFans> {

	public WeixinFans get(String id) {
		return super.get(id);
	}
	
	public List<WeixinFans> findList(WeixinFans weixinFans) {
		return super.findList(weixinFans);
	}
	
	public Page<WeixinFans> findPage(Page<WeixinFans> page, WeixinFans weixinFans) {
		return super.findPage(page, weixinFans);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinFans weixinFans) {
		super.save(weixinFans);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinFans weixinFans) {
		super.delete(weixinFans);
	}

	@Transactional(readOnly = false)
	public int deleteByAccountID(String accountId){
		return dao.deleteByAccountID(accountId);
	}
}