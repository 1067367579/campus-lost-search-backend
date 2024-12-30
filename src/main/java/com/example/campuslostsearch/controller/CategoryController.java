package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.result.Result;
import com.example.campuslostsearch.mapper.CategoryMapper;
import com.example.campuslostsearch.pojo.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping("/category/list")
    public Result<List<Category>> listCategory() {
        log.info("获取物品种类列表");
        List<Category> list = categoryMapper.listAll();
        return Result.success(list);
    }

    @GetMapping("/admin/category/list")
    public Result<List<Category>> adminListCategory() {
        log.info("管理员获取物品种类列表，无需状态限制");
        List<Category> list = categoryMapper.listAllWithoutStatus();
        return Result.success(list);
    }

    @PostMapping("/category")
    public Result<Category> addCategory(@RequestBody Category category) {
        log.info("添加类别：{}",category);
        category.setStatus(1);
        Integer categoryId = categoryMapper.insert(category);
        return Result.success(Category.builder().categoryId(categoryId).build());
    }

    @PutMapping("/admin/category/{id}")
    public Result<Object> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        log.info("根据ID：{},修改种类名:{}",id,category);
        category.setCategoryId(id);
        categoryMapper.update(category);
        return Result.success();
    }

    @PutMapping("/admin/category/{id}/status")
    public Result<Object> updateCategoryStatus(@PathVariable Integer id, @RequestBody Category category) {
        log.info("根据ID：{},修改类型状态:{}",id,category);
        category.setCategoryId(id);
        categoryMapper.update(category);
        return Result.success();
    }
}
