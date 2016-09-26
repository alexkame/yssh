/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 审批Entity
 * @author hailou
 * @version 2014-05-16
 */
public class ExpenseAudit extends ActEntity<ExpenseAudit> {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String userName;
	private String 	title;	//
	private String 	yuanyin;	//
	private String 	stepOne;	//
	private String 	stepTwo;	//
	private String 	stepThree;	//

	public ExpenseAudit() {
		super();
	}

	public ExpenseAudit(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYuanyin() {
		return yuanyin;
	}

	public void setYuanyin(String yuanyin) {
		this.yuanyin = yuanyin;
	}

	public String getStepOne() {
		return stepOne;
	}

	public void setStepOne(String stepOne) {
		this.stepOne = stepOne;
	}

	public String getStepTwo() {
		return stepTwo;
	}

	public void setStepTwo(String stepTwo) {
		this.stepTwo = stepTwo;
	}

	public String getStepThree() {
		return stepThree;
	}

	public void setStepThree(String stepThree) {
		this.stepThree = stepThree;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}


