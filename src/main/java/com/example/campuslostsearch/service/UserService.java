package com.example.campuslostsearch.service;

import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.pojo.dto.*;
import com.example.campuslostsearch.pojo.entity.User;
import com.example.campuslostsearch.pojo.vo.*;

public interface UserService {
    UserLoginVO login(UserLoginDTO userLoginDTO);

    void register(UserRegisterDTO userRegisterDTO);

    User getById(Long userId);

    void updateUserInfo(UserUpdateDTO userUpdateDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    PageResult<UserVO> listAdmin(Integer pageNum, Integer pageSize, String username,Integer status,Integer userType);

    void blockUser(BlockUserDTO blockUserDTO);

    void unlockUser(Long userId);

    void changeAdminStatus(Long userId,Integer status);

    UserBlackListVO getUserDetail(Long userId);

    StatisticAdminVO getStatistic();

    PageResult<OperationLogVO> listLogs(LogDTO build);
}
