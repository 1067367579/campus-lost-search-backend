package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.context.BaseContext;
import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.common.result.Result;
import com.example.campuslostsearch.pojo.dto.*;
import com.example.campuslostsearch.pojo.entity.User;
import com.example.campuslostsearch.pojo.vo.*;
import com.example.campuslostsearch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        UserLoginVO userLoginVO = userService.login(userLoginDTO);
        return Result.success(userLoginVO);
    }

    @PostMapping("/user/logout")
    public Result<Object> logout() {
        //登出操作
        return Result.success();
    }

    @PostMapping("/user/register")
    public Result<Object> register (@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户注册：{}",userRegisterDTO);
        userService.register(userRegisterDTO);
        return Result.success();
    }

    @GetMapping("/user/info")
    public Result<User> getUserInfo() {
        Long userId = BaseContext.getCurrentId();
        log.info("根据线程id获取当前用户信息:{}",userId);
        User user = userService.getById(userId);
        return Result.success(user);
    }

    @PutMapping("/user/info")
    public Result<Object> updateUserInfo(@RequestBody UserUpdateDTO userUpdateDTO) {
        Long userId = BaseContext.getCurrentId();
        log.info("用户ID为：{},修改用户信息：{}",userId,userUpdateDTO);
        userUpdateDTO.setUserId(userId);
        userService.updateUserInfo(userUpdateDTO);
        return Result.success();
    }

    @PutMapping("/user/password")
    public Result<Object> updatePassword(@RequestBody UserPasswordDTO userPasswordDTO) {
        Long userId = BaseContext.getCurrentId();
        log.info("用户ID为:{},修改密码:{}",userId,userPasswordDTO);
        userPasswordDTO.setUserId(userId);
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }

    @PostMapping("/admin/managers")
    public Result<Object> addAdmin(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("新增管理员:{}",userRegisterDTO);
        userRegisterDTO.setUserType(1);
        userService.register(userRegisterDTO);
        return Result.success();
    }

    @GetMapping("/admin/managers")
    public Result<PageResult<UserVO>> listAdmin(@RequestParam Integer pageNum,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam(required = false) String username) {
        log.info("查看所有管理员:{},{},{}",pageNum,pageSize,username);
        return Result.success(userService.listAdmin(pageNum,pageSize,username,null,1));
    }

    @GetMapping("/admin/users")
    public Result<PageResult<UserVO>> listUsers(@RequestParam Integer pageNum,
                                                @RequestParam Integer pageSize,
                                                @RequestParam(required = false) String username,
                                                @RequestParam(required = false) Integer status) {
        log.info("分页查询所有用户:{},{},{},{}",pageNum,pageSize,username,status);
        return Result.success(userService.listAdmin(pageNum,pageSize,username,status,0));
    }

    @GetMapping("/admin/users/{userId}")
    public Result<UserBlackListVO> getUserDetail(@PathVariable Long userId) {
        log.info("管理员查看用户详情:{}",userId);
        UserBlackListVO userBlackListVO = userService.getUserDetail(userId);
        return Result.success(userBlackListVO);
    }

    @PostMapping("/admin/users/{userId}/block")
    public Result<Object> blockUser(@PathVariable Long userId, @RequestBody BlockUserDTO blockUserDTO) {
        log.info("冻结用户，拉入黑名单：{}",userId);
        blockUserDTO.setUserId(userId);
        userService.blockUser(blockUserDTO);
        return Result.success();
    }

    @PostMapping("/admin/users/{userId}/unlock")
    public Result<Object> unlockUser(@PathVariable Long userId) {
        log.info("解冻用户：{}",userId);
        userService.unlockUser(userId);
        return Result.success();
    }

    @PutMapping("/admin/managers/{userId}/{status}")
    public Result<Object> blockAdmin(@PathVariable Long userId,@PathVariable Integer status) {
        log.info("改变管理员状态：{},{}",userId,status);
        status = (status == 1) ? 0 : 1;
        userService.changeAdminStatus(userId,status);
        return Result.success();
    }

    @GetMapping("/admin/dashboard/stats")
    public Result<StatisticAdminVO> getStatistic() {
        log.info("查看仪表台数据");
        return Result.success(userService.getStatistic());
    }

    @GetMapping("/admin/operation-logs")
    public Result<PageResult<OperationLogVO>> listLogs(@RequestParam Integer pageNum,
                                           @RequestParam Integer pageSize,
                                           @RequestParam(required = false) String operationType,
                                           @RequestParam(required = false) String username,
                                           @RequestParam(required = false) LocalDateTime startTime,
                                           @RequestParam(required = false) LocalDateTime endTime) {
        log.info("查询操作日志:{},{},{},{},{},{}",pageNum,pageSize,operationType,username,startTime,endTime);
        return Result.success(userService.listLogs(LogDTO.builder()
                        .pageNum(pageNum)
                        .pageSize(pageSize)
                        .operationType(operationType)
                        .username(username)
                        .startTime(startTime)
                        .endTime(endTime)
                .build()));
    }
}
