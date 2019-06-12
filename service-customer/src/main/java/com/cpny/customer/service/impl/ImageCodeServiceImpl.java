package com.cpny.customer.service.impl;

import com.cpny.common.cache.Cache;
import com.cpny.common.config.ComSystemConfig;
import com.cpny.common.enums.RandomTypeEnum;
import com.cpny.common.enums.RespEnum;
import com.cpny.common.exception.RetMsgException;
import com.cpny.common.utils.ImageUtil;
import com.cpny.customer.service.IImageCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;


/**
 * 图形验证码
 *
 * @author: wudc
 * @date:2019/3/6 19:50
 */
@Service
@Slf4j
public class ImageCodeServiceImpl implements IImageCodeService {

    @Autowired
    Cache cache;

    /**
     * 获取图形验证码
     * @param redisKey   redis缓存key
     * @param mobile     用户手机号码
     * @param productId  产品id
     * @param randomType CHAR, 纯字母 NUMBER,  纯数字  BLEND; 字母数字混合
     * @param time       缓存时间
     * @return
     * @throws IOException
     */
    @Override
    public String getImageCode(String redisKey, String mobile, Integer productId, String randomType, long time) throws IOException {
        RandomTypeEnum rType = RandomTypeEnum.getOf(randomType);
        // 生成验证码
        Map<String, BufferedImage> map = ImageUtil.createRandomImage(rType);
        if (1 == map.size()) {
            Map.Entry<String, BufferedImage> entry = map.entrySet().iterator().next();
            if (StringUtils.isNotBlank(entry.getKey())) {
                String imageCode = entry.getKey();
                if(!ComSystemConfig.isProd){//非生产环境, 验证码为8888.
                    imageCode = "8888";
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                log.info("图形验证码为:{}", imageCode);
                ImageIO.write(entry.getValue(), "jpeg", byteArrayOutputStream);
                String image = Base64.encodeBase64String(byteArrayOutputStream.toByteArray());
                // 缓存验证码
                boolean flag = cache.set(redisKey + mobile + "_" + productId, imageCode, time);
                if (!flag) {
                    log.info("忘记密码图形验证，redis存储失败");
                    throw new RetMsgException(RespEnum.ERROR);
                }
                return image;
            }
        }
        return "";
    }

    /**
     *
     * @param redisKey  redis缓存key
     * @param mobile    用户手机号码
     * @param productId 产品id
     * @param imageCode 图形验证码
     */
    @Override
    public void validImageCode(String redisKey, String mobile, Integer productId, String imageCode) {
        //获取缓存图形验证码
        String key = redisKey + mobile + "_" + productId;
        Object obj = cache.get(key);
        if (null == obj) {
            log.info("忘记支付密码  redis缓存不存在");
            throw new RetMsgException(RespEnum.IMAGE_CODE_NOT_EXIST);
        }
        String cacheImageCode = (String) obj;
        //判断图形验证是否相等
        if (!cacheImageCode.equals(imageCode)) {
            log.info("图形验证码输入有误");
            throw new RetMsgException(RespEnum.IMAGE_CODE_IS_ERROR);
        }
    }

    @Override
    public void delImageCode(String redisKey, String mobile, Integer productId) {
        //获取缓存图形验证码
        String key = redisKey + mobile + "_" + productId;
        //删除图形验证码
        cache.del(key);
    }
}
