package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.Result;
import com.example.campuslostsearch.dto.CategoryDTO;
import com.example.campuslostsearch.entity.Category;
import com.example.campuslostsearch.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<Category>> getAllCategories() {
        return Result.success(categoryService.getAllCategories());
    }

    @PostMapping
    public Result<Map<String, String>> createCategory(@RequestHeader("userId") String adminId,
                                                    @RequestBody CategoryDTO categoryDTO) {
        String categoryId = categoryService.createCategory(adminId, categoryDTO);
        Map<String, String> data = new HashMap<>();
        data.put("categoryId", categoryId);
        return Result.success(data);
    }

    @PutMapping("/{categoryId}")
    public Result<?> updateCategory(@RequestHeader("userId") String adminId,
                                  @PathVariable String categoryId,
                                  @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(adminId, categoryId, categoryDTO);
        return Result.success();
    }

    @PutMapping("/{categoryId}/status")
    public Result<?> updateCategoryStatus(@RequestHeader("userId") String adminId,
                                        @PathVariable String categoryId,
                                        @RequestParam Integer status) {
        categoryService.updateCategoryStatus(adminId, categoryId, status);
        return Result.success();
    }
} 