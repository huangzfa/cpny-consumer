package com.cpny.common.constant;

/**
 * 字典常量
 *
 * @author: jason
 * @date:2019/3/1 17:39
 */
public class Consts {

    //用户登录缓存信息, 后缀加上用户ID
    public static final String LOGIN_CACHE_KEY = "login_cache_";

    //忘记密码图形缓存信息，后缀加上用户手机号码和productId
    public static final String FORGET_PWD_IMAGE_CACHE_KEY = "forget_pwd_image_cache_";

    //忘记密码短信缓存信息，后缀加上用户手机号码和productId
    public static final String FORGET_PWD_MSG_CACHE_KEY = "forget_pwd_msg_cache_";

    //忘记密码短信缓存信息次数，后缀加上用户手机号码和productId
    public static final String FORGET_PWD_MSG_NUM_CACHE_KEY = "forget_pwd_msg_num_cache_";

    //操作类型，修改
    public static final String UPDATE = "update";

    //操作类型，添加
    public static final String ADD = "add";

    //操作类型，删除
    public static final String DELETE = "delete";

    //图形验证码缓存时间
    public static final Long IMAGE_CODE_CACHE_TIME = 30 * 60 * 1000L;

    //登录图形缓存信息，后缀加上用户手机号码和productId
    public static final String LOGIN_IMAGE_CACHE_KEY = "login_image_cache_";

    public static final String STRING_ZERO = "0";

    public static final String STRING_ONE = "1";

    public static final Integer INT_ZERO = 0;

    public static final Integer INT_ONE = 1;

    //分页大小
    public static final Integer pageSize = 15;


    //还款锁key
    public static final String REPAY_LOCK_KEY = "lock_repay_";


}
