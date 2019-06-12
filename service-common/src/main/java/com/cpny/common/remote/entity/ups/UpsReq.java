package com.cpny.common.remote.entity.ups;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 支付路由签约请求bo
 */
@Data
@Accessors(chain = true)
public class UpsReq implements Serializable {

    /**
     * 系统来源
     */
    private Integer productId;
    /**
     * 用户编码
     */
    private String userNo;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 银行卡号
     */
    private String bankCard;
    /**
     * 身份证号
     */
    private String identity;
    /**
     * 银行编码
     */
    private String bankCode;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 回调地址
     */
    private String notifyUrl;
    /**
     * 业务流水号
     */
    private String businessFlowNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 支付渠道
     */
    private String payChannel;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 短信编码
     */
    private String smsCode;


}
