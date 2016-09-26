/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.weixin.entity.WeixinMenuGroup;

/**
 * 菜单组DAO接口
 * @author HAILOU
 * @version 2016-09-19
 */
@MyBatisDao
public interface WeixinMenuGroupDao extends CrudDao<WeixinMenuGroup> {
	
}