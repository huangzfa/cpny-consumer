package com.cpny.common.remote.entity.ups;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付路由签约响应bo
 */
@Data
@Accessors(chain = true)
public class UpsRes {

    private String code;
    private String message;
    private Object result;

    public boolean isSuccess() {
        return "00".equals(this.code);
    }
}
