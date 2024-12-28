package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.entity.Complaint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ComplaintMapper {
    void insert(Complaint complaint);
    
    List<Complaint> findByUserId(@Param("userId") String userId,
                                @Param("status") Integer status,
                                @Param("offset") Integer offset,
                                @Param("limit") Integer limit);
                                
    Long countByUserId(@Param("userId") String userId,
                      @Param("status") Integer status);
                      
    List<Complaint> findPendingComplaints(@Param("offset") Integer offset,
                                         @Param("limit") Integer limit);
                                         
    Long countPendingComplaints();
    
    Complaint findById(String complaintId);
    
    void updateStatus(@Param("complaintId") String complaintId,
                     @Param("adminFeedback") String adminFeedback);
                     
    Long countByUserIdWithinPeriod(@Param("userId") String userId,
                                  @Param("startTime") LocalDateTime startTime);
} 