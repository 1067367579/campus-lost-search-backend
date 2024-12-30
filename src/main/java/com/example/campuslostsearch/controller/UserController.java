package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.context.BaseContext;
import com.example.campuslostsearch.common.result.Result;
import com.example.campuslostsearch.pojo.dto.UserLoginDTO;
import com.example.campuslostsearch.pojo.dto.UserPasswordDTO;
import com.example.campuslostsearch.pojo.dto.UserRegisterDTO;
import com.example.campuslostsearch.pojo.dto.UserUpdateDTO;
import com.example.campuslostsearch.pojo.entity.User;
import com.example.campuslostsearch.pojo.vo.UserLoginVO;
import com.example.campuslostsearch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        UserLoginVO userLoginVO = userService.login(userLoginDTO);
        return Result.success(userLoginVO);
    }

    @PostMapping("/logout")
    public Result<Object> logout() {
        //登出操作
        return Result.success();
    }

    @PostMapping("/register")
    public Result<Object> register (@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户注册：{}",userRegisterDTO);
        userService.register(userRegisterDTO);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<User> getUserInfo() {
        Long userId = BaseContext.getCurrentId();
        log.info("根据线程id获取当前用户信息:{}",userId);
        User user = userService.getById(userId);
        return Result.success(user);
    }

    @PutMapping("/info")
    public Result<Object> updateUserInfo(@RequestBody UserUpdateDTO userUpdateDTO) {
        Long userId = BaseContext.getCurrentId();
        log.info("用户ID为：{},修改用户信息：{}",userId,userUpdateDTO);
        userUpdateDTO.setUserId(userId);
        userService.updateUserInfo(userUpdateDTO);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<Object> updatePassword(@RequestBody UserPasswordDTO userPasswordDTO) {
        Long userId = BaseContext.getCurrentId();
        log.info("用户ID为:{},修改密码:{}",userId,userPasswordDTO);
        userPasswordDTO.setUserId(userId);
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }
}
