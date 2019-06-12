package com.cpny.common.enums;

/**
 * 响应枚举
 *
 * @author: jason
 * @date:2019/2/15 19:50
 */
public enum RespEnum {

    SUCCESS("1000", "处理成功"),

    FAILED("1001", "失败"),

    HANDLE_FAILED("1002", "业务处理失败!"),

    DATA_MISS("3000", "数据缺失，请从新登陆"),

    USER_ACCOUNT_NOT_EXIST("3001", "用户账号信息不存在"),

    USER_PAY_PWD_IS_ERROR("3002", "支付密码不正确请重新输入"),

    IS_ORIGINAL_PWD("3003", "请勿设置原密码组合"),

    IMAGE_CODE_NOT_EXIST("3004", "图形验证不存在，请重新获取"),

    IMAGE_CODE_IS_ERROR("3005", "图形验证码输入有误"),

    PWD_SMS_FREQUENCY_ERROR("3006", "发送忘记支付密码短信验证码上限，请明日再来"),

    PWD_SMS_OVERDUE("3007", "短信验证码已过期，请重新获取"),

    PWD_SMS_GET_ERROR("3008", "短信验证码错误,请重新输入"),

    UPDATE_ADDRESS_DATA_MISS("3009", "地址id为空"),

    ERROR("5001", "系统繁忙, 请稍后再试!"),

    NOT_LOGIN("5002", "请先登录."),

    DEFINE_MSG("5003", ""),//自定义消息, msg会更改, 勿用

    RE_LOGIN("5004", "您的账号已在其他设备登录!"),

    SERVICE_ERROR("5005", "服务繁忙, 请稍后再试!"),

    UN_CERTIFIED("5006", "未认证!"),

    CUSTOM("", ""),//自定义消息, msg会更改, 勿用

    POP_UP_MSG("2050", ""),


    ;

    private String code;
    private String msg;
    private boolean display;

    RespEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.display = true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    /**
     * 根据枚举的code返回枚举对象
     *
     * @param code 枚举code值
     * @return 枚举对象
     */
    public static RespEnum getEnumByCode(String code) {
        if (code == null) {
            return null;
        }
        for (RespEnum type : values()) {
            if (type.getCode().equals(code.trim())) {
                return type;
            }
        }
        return null;
    }


    public static RespEnum getDefineMsg(String Msg) {
        DEFINE_MSG.setMsg(Msg);
        return DEFINE_MSG;
    }

    public static RespEnum getCustom(String code, String Msg) {
        CUSTOM.setCode(code);
        CUSTOM.setMsg(Msg);
        return CUSTOM;
    }

}

