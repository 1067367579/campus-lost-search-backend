package com.example.campuslostsearch.service;

import com.example.campuslostsearch.pojo.dto.UserLoginDTO;
import com.example.campuslostsearch.pojo.dto.UserPasswordDTO;
import com.example.campuslostsearch.pojo.dto.UserRegisterDTO;
import com.example.campuslostsearch.pojo.dto.UserUpdateDTO;
import com.example.campuslostsearch.pojo.entity.User;
import com.example.campuslostsearch.pojo.vo.UserLoginVO;

public interface UserService {
    UserLoginVO login(UserLoginDTO userLoginDTO);

    void register(UserRegisterDTO userRegisterDTO);

    User getById(Long userId);

    void updateUserInfo(UserUpdateDTO userUpdateDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);

}
