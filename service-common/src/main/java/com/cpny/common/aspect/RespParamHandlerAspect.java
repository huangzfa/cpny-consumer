package com.cpny.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.cpny.common.entity.ReqParam;
import com.cpny.common.entity.ReqParam;
import com.cpny.common.entity.RespParam;
import com.cpny.common.enums.RespEnum;
import com.cpny.common.exception.RetMsgException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 响应参数处理aop
 * @author: jason
 * @date:2019/2/15 19:50
 */
@Slf4j
@Component
@Aspect
public class RespParamHandlerAspect {

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.cpny.common.annotation.RespParamHandler)")
    private void pointCutMethod() {
    }


    /**
     * 声明环绕通知
     *
     * @param pjp
     * @return
     */
    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) {

        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //请求url
        String url = request.getRequestURI();

        //请求方法
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        if (StringUtils.isEmpty(url)) {
            url = pjp.getTarget().getClass().getName() + "." + ms.getName();
        }

        //请求参数
        List<Object> reqParamList = new ArrayList<>();
        Object[] reqParams = pjp.getArgs();
        if (reqParams != null && reqParams.length > 0) {
            for (Object parameter : reqParams) {
                if (parameter instanceof ReqParam || parameter instanceof Integer || parameter instanceof String) {
                    reqParamList.add(parameter);
                }
            }
        }

//        //请求注解
//        Parameter[] parameters = ms.getMethod().getParameters();
//        if (parameters != null && parameters.length > 0) {
//            for (Parameter parameter : parameters) {
//                if (parameter.isAnnotationPresent(RequestBody.class)) {
//                    reqParamList.add(parameter);
//                }
//            }
//        }


        Long start = System.currentTimeMillis();
        RespParam resp = new RespParam();
        RespEnum enumResponse = RespEnum.SUCCESS;
        try {
            //如果业务返回RespParam对象, 则直接返回.
            Object resultObj = pjp.proceed();//执行业务方法
            if (resultObj instanceof RespParam) {
                resp = (RespParam) resultObj;
            } else {
                resp.setData(resultObj);//业务
            }
        } catch (RetMsgException e) {
             enumResponse = RespEnum.getCustom(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(url + "异常!", e);
            enumResponse = RespEnum.ERROR;
        } catch (Throwable e) {
            log.error(url + "异常!", e);
            enumResponse = RespEnum.ERROR;
        }
        if (StringUtils.isEmpty(resp.getCode())) {
            resp.setRespEnum(enumResponse);//返回信息赋值
        }
        Long useTime = System.currentTimeMillis() - start;
        log.info("请求url:{}, 入参:{}, 出参:{}, 耗时:[{}]毫秒", url, JSONObject.toJSONString(reqParamList), JSONObject.toJSONString(resp), useTime);
        return resp;
    }

}
