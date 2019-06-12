package com.cpny.common.exception;


/**
 * 处理失败的异常类
 * @author weijiasong
 * @date  2019年1月21日下午7:47:03
 */
public class HandleFailException extends RuntimeException {

	private static final long serialVersionUID = -3494488738551913370L;

	public HandleFailException(String msg){
		super(msg);
	}
	
}
