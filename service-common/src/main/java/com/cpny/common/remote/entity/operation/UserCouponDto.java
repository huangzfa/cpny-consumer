package com.cpny.common.remote.entity.operation;

import com.cpny.common.entity.ReqParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户优惠券
 * @author: jason
 * @date:2019/2/15 19:50
 */
@Getter
@Setter
public class UserCouponDto extends ReqParam {

    /** 券类型, jk：借款劵，hk：还款劵*/
    private String type;

    /** 使用金额 */
    private String useAmount;

    /** 用户ID */
    private String userId;

    private Long userCouponId;

}
