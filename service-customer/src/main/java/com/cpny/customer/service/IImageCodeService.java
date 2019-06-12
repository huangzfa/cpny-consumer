package com.cpny.customer.service;


import java.io.IOException;

/**
 * 获取图形验证码
 *
 * @author: wudc
 * @date:2019/3/6 19:50
 */
public interface IImageCodeService {

    /**
     * 获取图形验证码
     *
     * @param redisKey   redis缓存key
     * @param mobile     用户手机号码
     * @param productId  产品id
     * @param randomType CHAR, 纯字母 NUMBER,  纯数字  BLEND; 字母数字混合
     * @param time       缓存时间
     * @return
     */
    String getImageCode(String redisKey, String mobile, Integer productId, String randomType, long time) throws IOException;

    /**
     * 校验图形验证码
     *
     * @param redisKey  redis缓存key
     * @param mobile    用户手机号码
     * @param productId 产品id
     * @param imageCode 图形验证码
     * @return
     */
    void validImageCode(String redisKey, String mobile, Integer productId, String imageCode);

    void delImageCode(String redisKey, String mobile, Integer productId);

}
