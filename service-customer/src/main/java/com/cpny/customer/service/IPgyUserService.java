package com.cpny.customer.service;

import com.cpny.customer.dto.UserLoginDto;
import com.cpny.customer.dto.UserLoginResp;
import com.cpny.customer.entity.PgyUser;
import com.baomidou.mybatisplus.service.IService;

import java.io.IOException;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author huangzhongfa
 * @since 2019-06-11
 */
public interface IPgyUserService extends IService<PgyUser> {

     String imageCode(UserLoginDto reqParam) throws IOException;

    UserLoginResp login(UserLoginDto reqParam);
}
