package com.example.campuslostsearch.service.impl;

import com.example.campuslostsearch.common.context.BaseContext;
import com.example.campuslostsearch.common.properties.JwtProperties;
import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.common.utils.JwtUtil;
import com.example.campuslostsearch.exception.BaseException;
import com.example.campuslostsearch.mapper.BlackListMapper;
import com.example.campuslostsearch.mapper.UserMapper;
import com.example.campuslostsearch.pojo.dto.*;
import com.example.campuslostsearch.pojo.entity.BlackList;
import com.example.campuslostsearch.pojo.entity.User;
import com.example.campuslostsearch.pojo.vo.*;
import com.example.campuslostsearch.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;
    //密码加盐处理
    public static final String salt = "1b2i3t4e";
    @Autowired
    private BlackListMapper blackListMapper;

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

    @Override
    public PageResult<UserVO> listAdmin(Integer pageNum, Integer pageSize, String username,Integer status,Integer userType) {
        PageHelper.startPage(pageNum, pageSize);
        PageUserDTO pageUserDTO = PageUserDTO.builder()
                .username(username)
                .userType(userType)
                .status(status)
                .build();
        List<User> userList = userMapper.pageQuery(pageUserDTO);
        Page<User> page = (Page<User>) userList;
        long total = page.getTotal();
        List<User> records = page.getResult();
        List<UserVO> list = records.parallelStream().map((user) -> UserVO.builder()
                .username(user.getUsername())
                .userId(user.getUserId())
                .createTime(user.getCreateTime())
                .idNumber(user.getIdNumber())
                .status(user.getStatus())
                .build()).toList();
        return new PageResult<>(total,list);
    }

    @Override
    public void blockUser(BlockUserDTO blockUserDTO) {
        blockUserDTO.setAdminId(BaseContext.getCurrentId());
        blackListMapper.save(blockUserDTO);
    }

    @Override
    @Transactional
    public void unlockUser(Long userId) {
        blackListMapper.deleteByUserId(userId);
    }

    @Override
    public void changeAdminStatus(Long userId,Integer status) {
        userMapper.updateUser(User.builder()
                        .status(status)
                        .userId(userId)
                .build());
    }

    @Override
    @Transactional
    public UserBlackListVO getUserDetail(Long userId) {
        User user = this.getById(userId);
        BlackList blackList = blackListMapper.getByUserId(userId);
        UserBlackListVO userBlackListVO = UserBlackListVO.builder()
                .userId(userId)
                .username(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .status(user.getStatus())
                .createTime(user.getCreateTime())
                .build();
        if(blackList!=null) {
            User admin = userMapper.getById(blackList.getAdminId());
            userBlackListVO.setBlacklistCreateTime(blackList.getCreateTime());
            userBlackListVO.setType(blackList.getType());
            userBlackListVO.setReason(blackList.getReason());
            userBlackListVO.setOperatorName(admin.getUsername());
            userBlackListVO.setInBlacklist(true);
        }
        return userBlackListVO;
    }

    @Override
    public StatisticAdminVO getStatistic() {
        return userMapper.getStatisticAdmin();
    }

    @Override
    public PageResult<OperationLogVO> listLogs(LogDTO logDTO) {
        PageHelper.startPage(logDTO.getPageNum(), logDTO.getPageSize());
        List<OperationLogVO> operationLogVOList = userMapper.listLogs(logDTO);
        Page<OperationLogVO> page = (Page<OperationLogVO>) operationLogVOList;
        return new PageResult<>(page.getTotal(),page.getResult());
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
