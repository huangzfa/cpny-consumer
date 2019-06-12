package com.cpny.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.cpny.common.entity.RespParam;
import com.cpny.common.enums.RespEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 处理返回值
 */
@Slf4j
@Component
public class ResponseFilter extends ZuulFilter {
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        try {
            // 获取返回值内容，加以处理
            InputStream stream = context.getResponseDataStream();
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));

            if(StringUtils.isEmpty(body)){
                return null;
            }

            RespParam respParam = parseRespParam(body);
            if(respParam == null){
                return null;
            }

            //客户端对返回格式有要求, 所以改下.
            //data更改
            JSONObject dataJs = new JSONObject();
            dataJs.put("code", respParam.getCode());
            dataJs.put("msg", respParam.getMsg());
            dataJs.put("resp", respParam.getData());
            respParam.setData(dataJs);

            //外层code更改.
            if(!respParam.getState()){
                respParam.setRespEnum(RespEnum.SUCCESS);
            }

            //响应信息赋值.
            context.setResponseBody(respParam.toJson());
        } catch (Exception e) {
            log.error("返回信息处理异常!", e);
        }
        return null;
    }

    private RespParam parseRespParam(String body){
        RespParam respParam = null;
        try {
            respParam = JSONObject.parseObject(body, RespParam.class);
        } catch (Exception e) {}
        return respParam;
    }

    @Override
    public boolean shouldFilter() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        String requestURI = String.valueOf(ctx.get("requestURI"));
//        if (requestURI.contains("test")) { //不需要处理的URL请求，返回false
//            return false;
//        }
        return true;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 3;
    }

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;// 在请求被处理之后，会进入该过滤器
    }
}