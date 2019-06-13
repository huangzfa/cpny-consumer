package com.cpny.common.entity;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 请求参数
 * @author: jason
 * @date:2019/2/15 19:50
 */
@Getter
@Setter
public class ReqParam<T extends ReqParam<T>> implements Serializable {

	private static final long serialVersionUID = 3931162086397145872L;

	/** 请求头信息 */
	private ReqHeaderParam header;

	/* 分页参数 */
	/** 当前页 */
	private int curPage = 1;

	/** 每页显示数量 */
	private int pageSize = 10;

	public String toJson() {
		return JSON.toJSONString(this);
	}


}
