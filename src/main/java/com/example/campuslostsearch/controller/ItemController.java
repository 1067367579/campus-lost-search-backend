package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.Result;
import com.example.campuslostsearch.dto.ItemDTO;
import com.example.campuslostsearch.service.ItemService;
import com.example.campuslostsearch.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/lost")
    public Result<Map<String, String>> publishLostItem(@RequestHeader("userId") String userId,
                                                     @RequestBody ItemDTO itemDTO) {
        String itemId = itemService.publishLostItem(userId, itemDTO);
        Map<String, String> data = new HashMap<>();
        data.put("itemId", itemId);
        return Result.success(data);
    }

    @PostMapping("/found")
    public Result<Map<String, String>> publishFoundItem(@RequestHeader("userId") String userId,
                                                      @RequestBody ItemDTO itemDTO) {
        String itemId = itemService.publishFoundItem(userId, itemDTO);
        Map<String, String> data = new HashMap<>();
        data.put("itemId", itemId);
        return Result.success(data);
    }

    @GetMapping("/lost")
    public Result<List<ItemVO>> findLostItems(@RequestParam(required = false) String categoryId,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) Integer status,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(itemService.findLostItems(categoryId, keyword, status, pageNum, pageSize));
    }

    @GetMapping("/found")
    public Result<List<ItemVO>> findFoundItems(@RequestParam(required = false) String categoryId,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) Integer status,
                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(itemService.findFoundItems(categoryId, keyword, status, pageNum, pageSize));
    }
} 