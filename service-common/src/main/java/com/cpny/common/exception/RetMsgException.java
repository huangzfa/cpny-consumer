package com.cpny.common.exception;


import com.cpny.common.enums.RespEnum;

/**
 * 用于传递信息的异常类
 * @author weijiasong
 * @date  2019年1月21日下午7:47:03
 */
public class RetMsgException extends RuntimeException {

	private static final long serialVersionUID = -3494488738551913370L;

	private String code = RespEnum.DEFINE_MSG.getCode();

	public RetMsgException(String msg){
		super(msg);
	}

	public RetMsgException(String code, String msg){
		super(msg);
		this.code = code;
	}

	public RetMsgException(RespEnum respEnum){
		super(respEnum.getMsg());
		this.code = respEnum.getCode();
	}

	public String getCode(){
		return code;
	}

}
