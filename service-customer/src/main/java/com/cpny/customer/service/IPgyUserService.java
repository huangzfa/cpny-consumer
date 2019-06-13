package com.cpny.customer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cpny.customer.dto.UserLoginDto;
import com.cpny.customer.dto.UserLoginResp;
import com.cpny.customer.entity.PgyUser;

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

    IPage<PgyUser> getUserList(Integer page);
}
