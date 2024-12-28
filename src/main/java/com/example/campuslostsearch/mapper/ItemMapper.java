package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.entity.FoundItem;
import com.example.campuslostsearch.entity.LostItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemMapper {
    void insertLostItem(LostItem item);
    void insertFoundItem(FoundItem item);
    
    List<LostItem> findLostItems(@Param("categoryId") String categoryId, 
                                @Param("keyword") String keyword,
                                @Param("status") Integer status,
                                @Param("offset") Integer offset,
                                @Param("limit") Integer limit);
                                
    List<FoundItem> findFoundItems(@Param("categoryId") String categoryId,
                                  @Param("keyword") String keyword,
                                  @Param("status") Integer status,
                                  @Param("offset") Integer offset,
                                  @Param("limit") Integer limit);
                                  
    Long countLostItems(@Param("categoryId") String categoryId,
                       @Param("keyword") String keyword,
                       @Param("status") Integer status);
                       
    Long countFoundItems(@Param("categoryId") String categoryId,
                        @Param("keyword") String keyword,
                        @Param("status") Integer status);
} 