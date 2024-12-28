package com.example.campuslostsearch.service;

import com.example.campuslostsearch.dto.UserLoginDTO;
import com.example.campuslostsearch.dto.UserRegisterDTO;
import com.example.campuslostsearch.dto.UserUpdateDTO;
import com.example.campuslostsearch.entity.User;
import com.example.campuslostsearch.exception.BusinessException;
import com.example.campuslostsearch.mapper.UserMapper;
import com.example.campuslostsearch.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public String register(UserRegisterDTO registerDTO) {
        // 参数验证
        if (StringUtils.isBlank(registerDTO.getUsername())) {
            throw new BusinessException("用户名不能为空");
        }
        if (StringUtils.isBlank(registerDTO.getPassword())) {
            throw new BusinessException("密码不能为空");
        }
        if (StringUtils.isBlank(registerDTO.getIdNumber())) {
            throw new BusinessException("证件号不能为空");
        }
        if (!registerDTO.getIdNumber().matches("^\\d{18}$")) {
            throw new BusinessException("证件号格式不正确");
        }
        if (StringUtils.isBlank(registerDTO.getPhone())) {
            throw new BusinessException("手机号不能为空");
        }
        if (!registerDTO.getPhone().matches("^1[3-9]\\d{9}$")) {
            throw new BusinessException("手机号格式不正确");
        }
        if (StringUtils.isNotBlank(registerDTO.getEmail()) && 
            !registerDTO.getEmail().matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            throw new BusinessException("邮箱格式不正确");
        }

        // 业务验证
        if (userMapper.findByUsername(registerDTO.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }
        if (userMapper.findByIdNumber(registerDTO.getIdNumber()) != null) {
            throw new BusinessException("证件号已被注册");
        }
        
        // 处理业务
        User user = new User();
        user.setUserId(UUID.randomUUID().toString().replace("-", ""));
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setIdNumber(registerDTO.getIdNumber());
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setUserType(registerDTO.getUserType());
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        
        userMapper.insert(user);
        
        return jwtUtil.generateToken(user.getUserId(), user.getUsername(), user.getUserType());
    }

    public String login(UserLoginDTO loginDTO) {
        // 参数验证
        if (StringUtils.isBlank(loginDTO.getUsername())) {
            throw new BusinessException("用户名不能为空");
        }
        if (StringUtils.isBlank(loginDTO.getPassword())) {
            throw new BusinessException("密码不能为空");
        }

        // 业务验证
        User user = userMapper.findByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被冻结");
        }
        
        return jwtUtil.generateToken(user.getUserId(), user.getUsername(), user.getUserType());
    }

    public User getUserInfo(String userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    @Transactional
    public void updateUserInfo(String userId, UserUpdateDTO updateDTO) {
        // 参数验证
        if (StringUtils.isBlank(updateDTO.getPhone())) {
            throw new BusinessException("手机号不能为空");
        }
        if (!updateDTO.getPhone().matches("^1[3-9]\\d{9}$")) {
            throw new BusinessException("手机号格式不正确");
        }
        if (StringUtils.isNotBlank(updateDTO.getEmail()) && 
            !updateDTO.getEmail().matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            throw new BusinessException("邮箱格式不正确");
        }

        // 业务验证
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 处理业务
        user.setPhone(updateDTO.getPhone());
        user.setEmail(updateDTO.getEmail());
        
        userMapper.update(user);
    }
} 