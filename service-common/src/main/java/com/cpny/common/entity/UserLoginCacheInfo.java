package com.cpny.common.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录缓存信息
 * @author: jason
 * @date:2019/3/1 17:42
 */
@Data
@Accessors(chain = true)
public class UserLoginCacheInfo implements Serializable {

    //token
    private String token;
    //登录时间
    private Date loginTime;
    //用户ID
    private Long userId;
    //用户名
    private String userName;
    //设备ID
    private String deviceId;
    //appKey
    private String appKey;
    //缓存key
    private String loginCacheKey;
    //过期时间
    private Long expireTime;
    //产品ID
    private Integer productId;
    //应用ID
    private Integer appId;
}
