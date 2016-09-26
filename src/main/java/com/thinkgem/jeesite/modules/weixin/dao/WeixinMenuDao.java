/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.weixin.entity.WeixinMenu;

/**
 * 微信菜单DAO接口
 * @author HAILOU
 * @version 2016-09-20
 */
@MyBatisDao
public interface WeixinMenuDao extends TreeDao<WeixinMenu> {
	
}