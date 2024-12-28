package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.entity.AdminOperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AdminOperationLogMapper {
    void insert(AdminOperationLog log);
    
    List<AdminOperationLog> findByTimeRange(@Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime,
                                          @Param("offset") Integer offset,
                                          @Param("limit") Integer limit);
                                          
    Long countByTimeRange(@Param("startTime") LocalDateTime startTime,
                         @Param("endTime") LocalDateTime endTime);
} 