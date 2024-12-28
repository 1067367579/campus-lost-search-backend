package com.example.campuslostsearch.service;

import com.example.campuslostsearch.dto.CategoryDTO;
import com.example.campuslostsearch.entity.Category;
import com.example.campuslostsearch.exception.BusinessException;
import com.example.campuslostsearch.mapper.CategoryMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Autowired
    private AdminService adminService;

    @Transactional
    public String createCategory(String adminId, CategoryDTO categoryDTO) {
        if (StringUtils.isBlank(categoryDTO.getName())) {
            throw new BusinessException("类别名称不能为空");
        }
        
        if (categoryMapper.findByName(categoryDTO.getName()) != null) {
            throw new BusinessException("类别名称已存在");
        }
        
        Category category = new Category();
        category.setCategoryId(UUID.randomUUID().toString().replace("-", ""));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setStatus(1);
        
        categoryMapper.insert(category);
        
        adminService.logOperation(adminId, "创建物品类别", 
            String.format("创建物品类别：%s", category.getName()));
            
        return category.getCategoryId();
    }

    @Transactional
    public void updateCategory(String adminId, String categoryId, CategoryDTO categoryDTO) {
        if (StringUtils.isBlank(categoryDTO.getName())) {
            throw new BusinessException("类别名称不能为空");
        }
        if (StringUtils.isBlank(categoryId)) {
            throw new BusinessException("类别ID不能为空");
        }
        
        Category category = categoryMapper.findById(categoryId);
        if (category == null) {
            throw new BusinessException("物品类别不存在");
        }
        
        Category existingCategory = categoryMapper.findByName(categoryDTO.getName());
        if (existingCategory != null && !existingCategory.getCategoryId().equals(categoryId)) {
            throw new BusinessException("类别名称已存在");
        }
        
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        
        categoryMapper.update(category);
        
        adminService.logOperation(adminId, "更新物品类别", 
            String.format("更新物品类别：%s", category.getName()));
    }

    @Transactional
    public void updateCategoryStatus(String adminId, String categoryId, Integer status) {
        if (StringUtils.isBlank(categoryId)) {
            throw new BusinessException("类别ID不能为空");
        }
        if (status == null) {
            throw new BusinessException("状态不能为空");
        }
        if (status != 0 && status != 1) {
            throw new BusinessException("状态值无效");
        }
        
        Category category = categoryMapper.findById(categoryId);
        if (category == null) {
            throw new BusinessException("物品类别不存在");
        }
        
        categoryMapper.updateStatus(categoryId, status);
        
        adminService.logOperation(adminId, "更新物品类别状态", 
            String.format("更新物品类别[%s]状态为：%s", category.getName(), status == 1 ? "启用" : "禁用"));
    }

    public List<Category> getAllCategories() {
        return categoryMapper.findAll();
    }
} 