package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void insert(Category category);
    
    List<Category> findAll();
    
    Category findById(String categoryId);
    
    Category findByName(String name);
    
    void updateStatus(@Param("categoryId") String categoryId,
                     @Param("status") Integer status);
                     
    void update(Category category);
} 