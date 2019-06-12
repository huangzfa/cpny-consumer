package com.cpny.common.remote.entity.operation;

import lombok.Data;

import java.io.Serializable;

/**
 * 商户信息缓存
 * @author: jason
 * @date:2019/2/15 19:50
 */
@Data
public class MerchantCacheDto implements Serializable {

    private Long appId;
    private String appKey;
    private String appName;
    private Long productId;
    private String productName;
    private String productCode;
    private Long merchantId;
    private String merchantNo;
    private String merchantName;

    //缓存key
    public static final String CACHE_KEY = "cache_merchant_login";

    /**
     * 返回Item键值
     * @return
     */
    public String getItemKey(){
        return getItemKey(this.appKey);
    }

    public static String getItemKey(String appKey){
        return appKey;
    }

}
