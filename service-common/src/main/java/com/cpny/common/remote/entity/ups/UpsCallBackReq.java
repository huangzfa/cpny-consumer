package com.cpny.common.remote.entity.ups;

import lombok.Getter;
import lombok.Setter;

/**
 * ups回调请求参数
 * @author: jason
 * @date:2019/4/24 14:41
 */
@Getter
@Setter
public class UpsCallBackReq {

    private String merchantName;//来源商户名称
    private String payChannel;//支付渠道
    private String orderType;//订单类型
    private String upsOrderId;//UPS订单ID
    private String orderStatus;//订单状态
    private String channelResultMsg;//第三方支付渠道返回消息
    private String channelResultCode;//第三方支付渠道返回编码
    private String bussinessFlowNum;//业务唯一流水编码

    public boolean isSuccess(){
        return "success".equals(orderStatus);
    }

}
