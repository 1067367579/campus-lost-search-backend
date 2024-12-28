package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.Result;
import com.example.campuslostsearch.dto.UserLoginDTO;
import com.example.campuslostsearch.dto.UserRegisterDTO;
import com.example.campuslostsearch.dto.UserUpdateDTO;
import com.example.campuslostsearch.entity.User;
import com.example.campuslostsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<Map<String, String>> register(@RequestBody UserRegisterDTO registerDTO) {
        String token = userService.register(registerDTO);
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return Result.success(data);
    }

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody UserLoginDTO loginDTO) {
        String token = userService.login(loginDTO);
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return Result.success(data);
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestHeader("userId") String userId) {
        return Result.success(userService.getUserInfo(userId));
    }

    @PutMapping("/info")
    public Result<?> updateUserInfo(@RequestHeader("userId") String userId,
                                  @RequestBody UserUpdateDTO updateDTO) {
        userService.updateUserInfo(userId, updateDTO);
        return Result.success(null);
    }
} 