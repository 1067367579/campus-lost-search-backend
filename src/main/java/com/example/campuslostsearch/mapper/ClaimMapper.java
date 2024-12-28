package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.entity.Claim;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClaimMapper {
    void insert(Claim claim);
    
    List<Claim> findByUserId(@Param("userId") String userId,
                            @Param("status") Integer status,
                            @Param("offset") Integer offset,
                            @Param("limit") Integer limit);
                            
    Long countByUserId(@Param("userId") String userId,
                      @Param("status") Integer status);
                      
    Claim findById(String claimId);
    
    void updateStatus(@Param("claimId") String claimId,
                     @Param("status") Integer status);
                     
    List<Claim> findByItemId(@Param("itemId") String itemId,
                            @Param("itemType") Integer itemType);
    
    Long countPendingClaims();
    
    void updateLostItemStatus(@Param("itemId") String itemId, @Param("status") Integer status);
    void updateFoundItemStatus(@Param("itemId") String itemId, @Param("status") Integer status);
} 