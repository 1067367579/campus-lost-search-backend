package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.entity.Blacklist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlacklistMapper {
    void insert(Blacklist blacklist);
    
    List<Blacklist> findAll(@Param("offset") Integer offset,
                           @Param("limit") Integer limit);
                           
    Long countAll();
    
    Blacklist findByUserId(String userId);
} 