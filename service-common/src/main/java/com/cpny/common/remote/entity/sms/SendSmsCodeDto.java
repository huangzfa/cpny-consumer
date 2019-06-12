package com.cpny.common.remote.entity.sms;

import com.cpny.common.entity.ReqParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 发送短信验证码
 */
@Getter
@Setter
public class SendSmsCodeDto extends ReqParam {

    @NotBlank(message = "phone不能为空")
    @Pattern(regexp = "^1[0-9]{10}$", message = "phone格式不正确")
    private String phone;

    @NotBlank(message = "appKey不能为空")
    private String appKey;

    @NotBlank(message = "templetCode不能为空")
    private String templetCode;

    /** 短信验证码 */
    private String smsCode;
}
