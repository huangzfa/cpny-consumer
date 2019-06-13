package com.cpny.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpny.common.cache.Cache;
import com.cpny.common.constant.Consts;
import com.cpny.common.entity.UserLoginCacheInfo;
import com.cpny.common.enums.RandomTypeEnum;
import com.cpny.common.exception.RetMsgException;
import com.cpny.common.utils.TokenUtils;
import com.cpny.customer.dao.PgyUserDao;
import com.cpny.customer.dto.UserLoginDto;
import com.cpny.customer.dto.UserLoginResp;
import com.cpny.customer.entity.PgyUser;
import com.cpny.customer.service.IImageCodeService;
import com.cpny.customer.service.IPgyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author huangzhongfa
 * @since 2019-06-11
 */
@Service
@Slf4j
public class PgyUserServiceImpl extends ServiceImpl<PgyUserDao, PgyUser> implements IPgyUserService {

    @Autowired
    private IImageCodeService iImageCodeService;
    @Autowired
    private PgyUserDao userDao;
    @Autowired
    private Cache cache;

    /**
     * 获取图形验证码
     *
     * @param reqParam
     * @return
     * @throws IOException
     */
    @Override
    public String imageCode(UserLoginDto reqParam) throws IOException {
        return iImageCodeService.getImageCode(Consts.LOGIN_IMAGE_CACHE_KEY, reqParam.getPhone(), reqParam.getHeader().getProductId(), RandomTypeEnum.NUMBER.name(), Consts.IMAGE_CODE_CACHE_TIME);
    }

    /**
     *
     * @param reqParam
     * @return
     */
    @Override
    public UserLoginResp login(UserLoginDto reqParam){

        //3.x支持Lambda表达式
        QueryWrapper<PgyUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PgyUser::getUserNameEncrypt, "c+ErKv/Bc9/h5FnPDZlz/w==")
                .eq(PgyUser::getIsDelete,Consts.INT_ZERO);
        PgyUser loginUser = userDao.selectOne(queryWrapper);
        if( loginUser == null ){
            //注册
            loginUser = register(reqParam);
        }
        //缓存用户登录信息
        UserLoginCacheInfo userLoginCacheInfo = cacheLoginInfo(loginUser, reqParam);

        //响应信息
        UserLoginResp userLoginResp = new UserLoginResp();
        userLoginResp.setToken(userLoginCacheInfo.getToken());//token

        //调用短信服务>>清除短信验证码
        //smsService.cleanSmsCode(sendSmsCodeDto);

        //登录后业务, 异步
        //userHandler.loginAfter(loginUser, reqParam, IpUtil.getIpAddr());

        return userLoginResp;
    }

    /**
     * 注册
     * @param reqParam
     * @return
     */
    @Transactional
    public PgyUser register(UserLoginDto reqParam){
        //用户表添加数据.
        PgyUser loginUser = new PgyUser()

                .setChannelId(Consts.INT_ZERO);//渠道ID
        //先保存profile, 获取全局ID.
        if(userDao.insert(loginUser) < Consts.INT_ONE){
            throw new RetMsgException("您已注册, 请勿重复注册!");
        }
        //发送注册成功短信
        return loginUser;
    }

    /**
     * 缓存用户登录信息
     *
     * @param loginUser
     * @param reqParam
     * @return
     */
    private UserLoginCacheInfo cacheLoginInfo(PgyUser loginUser, UserLoginDto reqParam) {
        //redis缓存登录信息.
        String loginKey = Consts.LOGIN_CACHE_KEY + reqParam.getHeader().getAppId() + "_" + loginUser.getId();//缓存key
        //生成token
        String token = TokenUtils.encrypt(loginKey);
        //缓存用户登录信息.
        UserLoginCacheInfo userLoginCacheInfo = new UserLoginCacheInfo()
                .setLoginTime(new Date())//登录时间
                .setToken(token)
                .setUserId(loginUser.getId())
                .setDeviceId(reqParam.getHeader().getDeviceId())
                .setAppKey(reqParam.getHeader().getAppKey())
                .setLoginCacheKey(loginKey)
                .setExpireTime(60 * 60 * 24 * 30L)//过期时间30天.
                .setProductId(reqParam.getHeader().getProductId())//产品ID
                .setAppId(reqParam.getHeader().getAppId());//应用ID
        if (!cache.set(loginKey, userLoginCacheInfo, userLoginCacheInfo.getExpireTime())) {
            log.info("用户登录, 用户数据缓存失败!{}", reqParam.getPhone());
            throw new RetMsgException("登录失败3003!");
        }
        return userLoginCacheInfo;
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public IPage<PgyUser> getUserList(Integer page){
        IPage<PgyUser> list = new Page<>(page,Consts.pageSize);
        return userDao.selectPage(list,new QueryWrapper<PgyUser>()
                .select(PgyUser.ID,PgyUser.PRODUCT_ID)//查询部分字段
                .lambda()
                .eq(PgyUser::getIsDelete,Consts.INT_ZERO));//查询条件

    }

}
