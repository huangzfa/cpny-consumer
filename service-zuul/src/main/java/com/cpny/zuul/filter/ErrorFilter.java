package com.cpny.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理
 */
@Slf4j
@Component
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //请求url
        String reqUrl = request.getRequestURI();

        try {

            //上抛异常
            Throwable throwable = ctx.getThrowable();
            throw throwable;

        } catch (ZuulException ex) {
            ctx.set("error.status_code", ex.nStatusCode);
            ctx.set("error.message", ex.errorCause);
            ctx.set("error.exception", ex);
        } catch (Throwable ex) {
            ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.exception", ex);
        }
        return null;
    }

}