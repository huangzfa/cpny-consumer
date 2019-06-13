package com.cpny.customer.controller;


import com.cpny.common.annotation.RespParamHandler;
import com.cpny.customer.dto.UserLoginDto;
import com.cpny.customer.service.IPgyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author huangzhongfa
 * @since 2019-06-11
 */
@RestController
@RequestMapping("/pgyUser")
public class PgyUserController {

    @Autowired
    IPgyUserService userService;

    /**
     * 获取图形验证码
     *
     * @param reqParam
     * @return
     */
    @RespParamHandler
    @PostMapping(value = "/imageCode")
    public Object imageCode(@Valid @RequestBody UserLoginDto reqParam) throws IOException {
        return userService.imageCode(reqParam);
    }

    /**
     * 登录
     *
     * @param userLoginDto
     * @return
     */
    @RespParamHandler
    @PostMapping(value = "/login")
    public Object login(@Valid @RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RespParamHandler
    @PostMapping(value = "/getUserList")
    public Object getUserList(Integer page) {
        return userService.getUserList(page);
    }
}

