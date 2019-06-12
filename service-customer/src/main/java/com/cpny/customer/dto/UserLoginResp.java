package com.cpny.customer.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户登录出参
 * @author: jason
 * @date:2019/2/15 19:50
 */
@Getter
@Setter
public class UserLoginResp implements Serializable {

    private String token;

}
