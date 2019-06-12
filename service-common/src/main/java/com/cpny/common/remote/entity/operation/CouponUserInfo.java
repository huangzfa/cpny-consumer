package com.cpny.common.remote.entity.operation;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户优惠券信息
 * @author: jason
 * @date:2019/3/13 14:55
 */
@Getter
@Setter
public class CouponUserInfo {

    /** 优惠券金额 */
    private String amount;
    /** 使用说明 */
    private String useExplain;
    /** 优惠券名称 */
    private String name;
    /** 使用限额 */
    private String limitAmount;
    /** 使用有效开始日期 */
    private Date startTime;
    /** 使用有效结束日期 */
    private Date endTime;
    /** 使用有效开始日期 */
    private String startTimeStr;
    /** 使用有效结束日期 */
    private String endTimeStr;
    /** 优惠券ID */
    private String id;
    /** 优惠券类型, jk：借款劵，hk：还款劵 */
    private String type;
    /** 劵状态 【 0：未使用，1：已使用，2：冻结，3：过期 】 */
    private String state;

}
