package com.cpny.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Description TODO
 * @author ChongLou
 * @date 2018年11月28日
 */
public enum RandomTypeEnum {

	CHAR, // 纯字母

	NUMBER, // 纯数字

	BLEND; // 字母数字混合

	public static RandomTypeEnum getOf(String type) {
		try {
			if (StringUtils.isBlank(type)) {
				return RandomTypeEnum.NUMBER;
			}
			return RandomTypeEnum.valueOf(type.toUpperCase().trim());
		} catch (Exception e) {
			return RandomTypeEnum.NUMBER;
		}

	}
}
