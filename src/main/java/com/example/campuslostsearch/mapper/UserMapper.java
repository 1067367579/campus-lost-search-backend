package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    User findByIdNumber(String idNumber);
    void insert(User user);
    void updateStatus(@Param("userId") String userId, @Param("status") Integer status);
    User findById(String userId);
    void update(User user);
} 