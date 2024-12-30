package com.example.campuslostsearch.service.impl;

import com.example.campuslostsearch.common.properties.JwtProperties;
import com.example.campuslostsearch.common.utils.JwtUtil;
import com.example.campuslostsearch.exception.BaseException;
import com.example.campuslostsearch.mapper.UserMapper;
import com.example.campuslostsearch.pojo.dto.UserLoginDTO;
import com.example.campuslostsearch.pojo.dto.UserPasswordDTO;
import com.example.campuslostsearch.pojo.dto.UserRegisterDTO;
import com.example.campuslostsearch.pojo.dto.UserUpdateDTO;
import com.example.campuslostsearch.pojo.entity.User;
import com.example.campuslostsearch.pojo.vo.UserLoginVO;
import com.example.campuslostsearch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;
    //密码加盐处理
    public static final String salt = "1b2i3t4e";

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        //数据的有效性已在前端校验
        String password = userLoginDTO.getPassword();
        String MD5Password = getMd5Password(password);
        userLoginDTO.setPassword(MD5Password);
        //去用户表中根据组合获取对象
        User user = userMapper.getByLoginDTO(userLoginDTO);
        if(user == null) {
            throw new BaseException("用户不存在或密码错误！");
        }
        if(user.getStatus() == 0) {
            throw new BaseException("用户冻结！");
        }
        //生成令牌
        String token = generateToken(user.getUserId());
        //返回VO对象
        return UserLoginVO.builder()
                .token(token)
                .username(user.getUsername())
                .userId(user.getUserId())
                .userType(user.getUserType())
                .status(user.getStatus())
                .build();
    }

    @Override
    @Transactional
    public void register(UserRegisterDTO userRegisterDTO) {
        //数据格式已在前端校验完毕
        //先看用户名或身份证号是否已经存在
        User user = userMapper.getByUsername(userRegisterDTO.getUsername());
        if(user!=null) {
            throw new BaseException("用户名已存在！");
        }
        user = userMapper.getByIdNumber(userRegisterDTO.getIdNumber());
        if(user!=null) {
            throw new BaseException("学号已绑定！");
        }
        //验证重复性通过 将数据放入数据库
        user = User.builder()
                .username(userRegisterDTO.getUsername())
                .password(getMd5Password(userRegisterDTO.getPassword()))
                .userType(userRegisterDTO.getUserType())
                .email(userRegisterDTO.getEmail())
                .phone(userRegisterDTO.getPhone())
                .idNumber(userRegisterDTO.getIdNumber())
                .build();
        userMapper.save(user);
    }

    @Override
    public User getById(Long userId) {
        return userMapper.getById(userId);
    }

    @Override
    public void updateUserInfo(UserUpdateDTO userUpdateDTO) {
        userMapper.updateUser(User.builder()
                        .userId(userUpdateDTO.getUserId())
                        .phone(userUpdateDTO.getPhone())
                        .email(userUpdateDTO.getEmail())
                        .build());
    }

    @Override
    @Transactional
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        //验证老密码是否正确
        User user = userMapper.getById(userPasswordDTO.getUserId());
        if(user == null) {
            throw new BaseException("用户不存在！");
        }
        userPasswordDTO.setOldPassword(getMd5Password(userPasswordDTO.getOldPassword()));
        if(!user.getPassword().equals(userPasswordDTO.getOldPassword())) {
            throw new BaseException("旧密码输入错误！");
        }
        userMapper.updateUser(User.builder()
                        .userId(userPasswordDTO.getUserId())
                        .password(getMd5Password(userPasswordDTO.getNewPassword()))
                        .build());
    }

    public String generateToken(Long userId) {
        //claims,生成jwt绑定的一些身份信息
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("userId",userId);
        //生成Jwt令牌
        return JwtUtil.createJWT(jwtProperties.getSecret(), jwtProperties.getExpiration()
                , claims);
    }

    //对密码进行加密处理
    private static String getMd5Password(String password) {
        //密码加盐处理
        String saltStr = salt.charAt(1)+salt.charAt(3)+ password +salt.charAt(5)+salt.charAt(7);
        //加密密码并放入DTO中
        String MD5Password = DigestUtils.md5DigestAsHex(saltStr.getBytes());
        log.info("加密的密码：{}",MD5Password);
        return MD5Password;
    }
}
