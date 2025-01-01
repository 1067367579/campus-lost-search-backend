package com.example.campuslostsearch.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CommonMapper {
    @Update("SET @admin_id = #{adminId}")
    void setAdminId(Long adminId);
}
