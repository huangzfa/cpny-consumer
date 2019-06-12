package com.cpny.common.remote.entity.sms;

import java.io.Serializable;
import java.util.Map;

/**
 * 发送短信Dto
 * @author weijiasong
 * @date  2019年1月21日下午7:19:40
 */
public class SendSmsDto implements Serializable{

	private static final long serialVersionUID = -6921177656799503058L;
	
	/**
	 * 应用key
	 */
	private String appKey;
	
	/**
	 * 短信业务类别: 催收:collection, 营销:marketing, 常规:normal. 
	 */
	private String businessCode;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 短信模板
	 */
	private String templetCode;
	
	/**
	 * 短信填充内容
	 */
	private Map<String, String> contentMap;
	
	/**
	 * 短信内容
	 */
	private String content;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTempletCode() {
		return templetCode;
	}

	public void setTempletCode(String templetCode) {
		this.templetCode = templetCode;
	}

	public Map<String, String> getContentMap() {
		return contentMap;
	}

	public void setContentMap(Map<String, String> contentMap) {
		this.contentMap = contentMap;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
