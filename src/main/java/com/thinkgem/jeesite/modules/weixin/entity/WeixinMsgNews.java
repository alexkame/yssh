/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weixin.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微信图文Entity
 * @author HAILOU
 * @version 2016-09-19
 */
public class WeixinMsgNews extends DataEntity<WeixinMsgNews> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String author;		// 作者
	private String brief;		// 简介
	private String description;		// 内容
	private String picpath;		// 图片地址
	private String showpic;		// 封面图片
	private String url;		// URL
	private String fromurl;		// 原文链接
	private String baseId;		// 关键词
	private String baseName;

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public WeixinMsgNews() {
		super();
	}

	public WeixinMsgNews(String id){
		super(id);
	}

	@Length(min=1, max=64, message="标题长度必须介于 1 和 64 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="作者长度必须介于 0 和 255 之间")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Length(min=0, max=255, message="简介长度必须介于 0 和 255 之间")
	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	@Length(min=0, max=255, message="内容长度必须介于 0 和 255 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=255, message="图片地址长度必须介于 0 和 255 之间")
	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	
	@Length(min=0, max=255, message="封面图片长度必须介于 0 和 255 之间")
	public String getShowpic() {
		return showpic;
	}

	public void setShowpic(String showpic) {
		this.showpic = showpic;
	}
	
	@Length(min=0, max=255, message="URL长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=255, message="原文链接长度必须介于 0 和 255 之间")
	public String getFromurl() {
		return fromurl;
	}

	public void setFromurl(String fromurl) {
		this.fromurl = fromurl;
	}
	
	@Length(min=1, max=64, message="关键词长度必须介于 1 和 64 之间")
	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	
}