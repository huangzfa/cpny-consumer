package com.cpny.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 请求header参数
 * @author: jason
 * @date:2019/2/22 15:38
 */
@Getter
@Setter
public class ReqHeaderParam implements Serializable {

    /** 请求id */
    private String id;

    /** 应用Key */
    private String appKey;

    /** app版本 */
    private String appVersion;

    /** 渠道Code */
    private String channelCode;

    /** 设备ID */
    private String deviceId;

    /** 网络类型 */
    private String netType;

    /** 请求时间戳 */
    private String time;

    /** 请求token */
    private String token;

    /** 请求签名 */
    private String sign;

    /** 系统类型 */
    private String appOsType;

    //下面是不需要客户端传的数据---

    /** 用户名 */
    private String userName;

    /** 用户Id */
    private Long userId;

    /** 应用Id */
    private Integer appId;

    /** 产品Id */
    private Integer productId;

    /**产品编码*/
    private String productCode;

    /**商户编码*/
    private String 	merchantNo;

    /**商户ID*/
    private Integer merchantId;

}
