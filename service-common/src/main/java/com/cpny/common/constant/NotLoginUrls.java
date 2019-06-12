package com.cpny.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 不用登录的urls
 * @author: jason
 * @date:2019/3/5 11:36
 */
public class NotLoginUrls {

    //不用登录的url
    public final static List<String> NOT_LOGIN_URLS = new ArrayList<String>();

    static{

        //用户服务
        NOT_LOGIN_URLS.add("/customer/user/login");//登录
        NOT_LOGIN_URLS.add("/customer/user/imageCode");//登录获取图形验证码
        NOT_LOGIN_URLS.add("/customer/user/sendSmsCode");//登录发送验证码.
    }


}
