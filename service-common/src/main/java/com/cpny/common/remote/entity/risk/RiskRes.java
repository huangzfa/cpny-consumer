package com.cpny.common.remote.entity.risk;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RiskRes {

    private String msg;

    private String code;

    private Boolean success;

    private JSONObject data;
    /**
     * 是否降级
     */
    private boolean isDowngrade;

    public boolean isSuccess() {
        return success && "0000".equals(code);
    }
}
