package com.cpny.common.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.cpny.common.enums.RespEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 响应参数
 * @author: jason
 * @date:2019/2/15 19:50
 */
@Getter
@Setter
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RespParam<T> implements Serializable{

	private static final long serialVersionUID = -2371163055421043552L;

	private String code;
	private String msg;
	private T data;

	public RespParam(){}

	public RespParam(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public RespParam(RespEnum respEnum){
		this.code = respEnum.getCode();
		this.msg = respEnum.getMsg();
	}

	public void setRespEnum(RespEnum respEnum){
		this.code = respEnum.getCode();
		this.msg = respEnum.getMsg();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public Boolean getState() {
		return RespEnum.SUCCESS.getCode().equals(this.code);
	}

	public String toJson() {
		return JSON.toJSONString(this);
	}
}
