package com.cpny.customer.dto;

import com.cpny.common.entity.ReqParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户登录入参
 * @author: jason
 * @date:2019/2/15 19:50
 */
@Getter
@Setter
public class UserLoginDto extends ReqParam {

    @NotBlank(message = "phone不能为空")
    @Pattern(regexp = "^1[0-9]{10}$", message = "phone格式不正确")
    private String phone;

    private String smsCode;

    /** 手机型号：如iPhone6s,HUAWEI GRA-CL00 */
    private String phoneType;


}
