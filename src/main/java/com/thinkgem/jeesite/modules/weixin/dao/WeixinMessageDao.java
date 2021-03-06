/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.weixin.entity.WeixinMessage;

/**
 * 微信推送信息DAO接口
 * @author HAILOU
 * @version 2016-09-20
 */
@MyBatisDao
public interface WeixinMessageDao extends CrudDao<WeixinMessage> {
	
}