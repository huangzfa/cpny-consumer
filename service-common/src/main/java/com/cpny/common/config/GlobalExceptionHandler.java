package com.cpny.common.config;

import com.cpny.common.entity.RespParam;
import com.cpny.common.enums.RespEnum;
import com.cpny.common.exception.RetMsgException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 异常捕获
 *
 * @author: jason
 * @date:2019/2/18 16:50
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理请求对象属性不满足校验规则的异常信息
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public RespParam exception(HttpServletRequest request, MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        if (null == fieldErrors || fieldErrors.size() == 0) {
            return new RespParam(RespEnum.ERROR);
        }
        return new RespParam(RespEnum.DEFINE_MSG.getCode(), fieldErrors.stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.joining(",")));
    }

    /**
     * 处理请求单个参数不满足校验规则的异常信息
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public RespParam constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException exception) {
        log.info(exception.getMessage(), exception);
        return new RespParam(RespEnum.DEFINE_MSG.getCode(), exception.getMessage());
    }


    /**
     * 业务自定义异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = RetMsgException.class)
    public RespParam constraintViolationExceptionHandler(RetMsgException exception) {
        log.info(exception.getMessage(), exception);
        return new RespParam(exception.getCode(), exception.getMessage());
    }

    /**
     * 处理未定义的其他异常信息
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public RespParam exceptionHandler(HttpServletRequest request, Exception exception) {
        log.error(exception.getMessage(), exception);
        if (exception instanceof HttpRequestMethodNotSupportedException) {
            //请求方式不正确
            return new RespParam(RespEnum.ERROR.getCode(), "请求方式不正确");
        }
        if (exception instanceof HttpMessageNotReadableException) {
            //请求参数不正确
            return new RespParam(RespEnum.ERROR.getCode(), "请求参数格式不正确");
        }
        return new RespParam(RespEnum.ERROR);
    }

}