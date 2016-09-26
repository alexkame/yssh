/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.weixin.entity.WeixinAccount;
import com.thinkgem.jeesite.modules.weixin.dao.WeixinAccountDao;

/**
 * 公众账号管理Service
 * @author HAILOU
 * @version 2016-09-19
 */
@Service
@Transactional(readOnly = true)
public class WeixinAccountService extends CrudService<WeixinAccountDao, WeixinAccount> {

	public WeixinAccount get(String id) {
		return super.get(id);
	}
	
	public List<WeixinAccount> findList(WeixinAccount weixinAccount) {
		return super.findList(weixinAccount);
	}
	
	public Page<WeixinAccount> findPage(Page<WeixinAccount> page, WeixinAccount weixinAccount) {
		return super.findPage(page, weixinAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinAccount weixinAccount) {
		super.save(weixinAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinAccount weixinAccount) {
		super.delete(weixinAccount);
	}
	
}