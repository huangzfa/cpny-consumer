package com.cpny.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.cpny.common.cache.Cache;
import com.cpny.common.config.ComSystemConfig;
import com.cpny.common.constant.NotLoginUrls;
import com.cpny.common.entity.ReqHeaderParam;
import com.cpny.common.entity.RespParam;
import com.cpny.common.entity.UserLoginCacheInfo;
import com.cpny.common.enums.RespEnum;
import com.cpny.common.exception.RetMsgException;
import com.cpny.common.remote.entity.operation.MerchantCacheDto;
import com.cpny.common.utils.TokenUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.apache.catalina.servlet4preview.http.HttpServletRequestWrapper;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

@Component
public class TokenValidFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public Cache cache;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.getResponse().setContentType("text/html;charset=UTF-8");
        HttpServletRequest request = ctx.getRequest();
        //请求url
        String reqUrl = request.getRequestURI();
        log.info("请求信息:{} >> {}", request.getMethod(), reqUrl);

        try {

            //token验证
            UserLoginCacheInfo userLoginCacheInfo =  validToken();

            //重新构建请求参数.
            reBuildReqParam(userLoginCacheInfo);

        } catch (RetMsgException e) {
            ctx.setSendZuulResponse(false);
            RespParam resp = new RespParam(RespEnum.getCustom(e.getCode(), e.getMessage()));
            ctx.setResponseBody(resp.toJson());
            log.info("{}请求被拦截!响应信息:{}", reqUrl, resp.toJson());
        } catch (Exception e) {
            log.error("系统异常!", e);
            ctx.setSendZuulResponse(false);
            RespParam resp = new RespParam(RespEnum.ERROR);
            ctx.setResponseBody(resp.toJson());
        }
        return null;
    }

    /**
     * 重新构建请求参数
     * @param userLoginCacheInfo
     * @throws IOException
     */
    private void reBuildReqParam(UserLoginCacheInfo userLoginCacheInfo) {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        //获取请求信息.
        String body = getBody(ctx);
        JSONObject requestBody = JSONObject.parseObject(body);

        //设置header信息.
        JSONObject reqHeader = new JSONObject();
        requestBody.put("header", reqHeader);

        //请求header信息赋值.
        Field[] fields = ReqHeaderParam.class.getDeclaredFields();
        for (Field field : fields) {
            reqHeader.put(field.getName(), request.getHeader(field.getName()));
        }

        //用户信息赋值
        if(userLoginCacheInfo != null){
            reqHeader.put("userName", userLoginCacheInfo.getUserName());
            reqHeader.put("userId", userLoginCacheInfo.getUserId());
        }

        //产品信息赋值
        String appKey = ObjectUtils.toString(reqHeader.get("appKey"));
        if(StringUtils.isEmpty(appKey)){
            throw new RetMsgException("appKey不能为空!");
        }
        //获取商户信息
        /*MerchantCacheDto merchantCacheDto = (MerchantCacheDto) cache.hget(MerchantCacheDto.CACHE_KEY, MerchantCacheDto.getItemKey(appKey));
        if(merchantCacheDto == null){
            throw new RetMsgException("appKey不存在!");
        }
        if(userLoginCacheInfo != null && userLoginCacheInfo.getProductId() != null && merchantCacheDto.getProductId() != null){
            if(!merchantCacheDto.getProductId().equals(userLoginCacheInfo.getProductId().longValue())){
                throw new RetMsgException("token异常, 非当前产品的token!");
            }
        }*/
        //reqHeader.put("appId", merchantCacheDto.getAppId());
       /* reqHeader.put("productId", merchantCacheDto.getProductId());//产品ID
        reqHeader.put("productCode", merchantCacheDto.getProductCode());//产品编码
        reqHeader.put("merchantNo", merchantCacheDto.getMerchantNo());//商户编码
        reqHeader.put("merchantId", merchantCacheDto.getMerchantId());//商户ID*/

        //设置到request中.
        final byte[] reqBodyBytes = requestBody.toString().getBytes();
        ctx.setRequest(new HttpServletRequestWrapper(request){
            @Override
            public ServletInputStream getInputStream() throws IOException {
                return new ServletInputStreamWrapper(reqBodyBytes);
            }
            @Override
            public int getContentLength() {
                return reqBodyBytes.length;
            }
            @Override
            public long getContentLengthLong() {
                return reqBodyBytes.length;
            }
        });

    }

    /**
     * token验证
     */
    private UserLoginCacheInfo validToken(){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        //请求url
        String reqUrl = request.getRequestURI();

        //token
        String token = request.getHeader("token");

        //从缓存中获取登录信息
        UserLoginCacheInfo userLoginCacheInfo = getUserLoginCacheInfo(token);

        //开发环境不校验token
        if(ComSystemConfig.isDev){
            if(userLoginCacheInfo == null){
                userLoginCacheInfo = new UserLoginCacheInfo();
                userLoginCacheInfo.setUserId(21L);
                userLoginCacheInfo.setUserName("15268522304");
                return userLoginCacheInfo;
            }
        }

        //判断url是否需要验证token
        if(!NotLoginUrls.NOT_LOGIN_URLS.contains(reqUrl)){
            //需要拦截的url
            //未登录
            if(StringUtils.isEmpty(token)) {
                throw new RetMsgException(RespEnum.NOT_LOGIN);
            }
            //token失效
            if(userLoginCacheInfo == null){
                throw new RetMsgException(RespEnum.NOT_LOGIN.getCode(), "登录信息已过期, 请重新登录!");
            }
            //单点登录
            if(!token.equals(userLoginCacheInfo.getToken())){
//                    userLoginCacheInfo.getLoginTime();//登录时间
                throw new RetMsgException(RespEnum.RE_LOGIN);//单点登录
            }
            if(!StringUtils.isEmpty(userLoginCacheInfo.getDeviceId()) && !userLoginCacheInfo.getDeviceId().equals(request.getHeader("deviceId"))){
                throw new RetMsgException(RespEnum.RE_LOGIN.getCode(), "请求设备异常!");//设备更换.
            }
            //校验通过, 刷新token过期时间
            if(!StringUtils.isEmpty(userLoginCacheInfo.getLoginCacheKey())){
                cache.expire(userLoginCacheInfo.getLoginCacheKey(), userLoginCacheInfo.getExpireTime());
            }
        }
        return userLoginCacheInfo;

    }

    /**
     * 获取登录信息
     * @param token
     * @return
     */
    private UserLoginCacheInfo getUserLoginCacheInfo(String token){
        UserLoginCacheInfo userLoginCacheInfo = null;
        try {
            if (!StringUtils.isEmpty(token)) {
                //解析token获取登录缓存信息key
                String loginCacheKey = TokenUtils.decrypt(token);
                userLoginCacheInfo = (UserLoginCacheInfo) cache.get(loginCacheKey);
            }
        } catch (Exception e) {
            log.error("获取登录缓存信息异常!", e);
        }

        return userLoginCacheInfo;
    }

    /**
     * 获取body
     * @param ctx
     * @return
     */
    private String getBody(RequestContext ctx){
        String body = "{}";
        try {
            InputStream in = ctx.getRequest().getInputStream();
            body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            if(StringUtils.isEmpty(body)){
                body = "{}";
            }
        } catch (Exception e){
            log.error("获取body异常!", e);
        }
        return body;
    }
}