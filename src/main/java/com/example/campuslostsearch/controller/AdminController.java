package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.Result;
import com.example.campuslostsearch.dto.BlacklistDTO;
import com.example.campuslostsearch.service.AdminService;
import com.example.campuslostsearch.service.BlacklistService;
import com.example.campuslostsearch.vo.AdminOperationLogVO;
import com.example.campuslostsearch.vo.AdminStatsVO;
import com.example.campuslostsearch.vo.BlacklistVO;
import com.example.campuslostsearch.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private BlacklistService blacklistService;

    @GetMapping("/pending-stats")
    public Result<AdminStatsVO> getPendingStats() {
        return Result.success(adminService.getPendingStats());
    }

    @GetMapping("/operation-logs")
    public Result<PageVO<AdminOperationLogVO>> getOperationLogs(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        if (startTime == null) {
            return Result.error("开始时间不能为空");
        }
        if (endTime == null) {
            return Result.error("结束时间不能为空");
        }
        if (endTime.isBefore(startTime)) {
            return Result.error("结束时间不能早于开始时间");
        }
        if (pageNum < 1) {
            return Result.error("页码必须大于0");
        }
        if (pageSize < 1) {
            return Result.error("每页条数必须大于0");
        }
        return Result.success(adminService.getOperationLogs(startTime, endTime, pageNum, pageSize));
    }

    @PostMapping("/blacklist")
    public Result<?> addToBlacklist(@RequestHeader("userId") String adminId,
                                   @RequestBody BlacklistDTO blacklistDTO) {
        if (StringUtils.isBlank(adminId)) {
            return Result.error("管理员ID不能为空");
        }
        if (blacklistDTO == null) {
            return Result.error("黑名单信息不能为空");
        }
        if (StringUtils.isBlank(blacklistDTO.getUserId())) {
            return Result.error("用户ID不能为空");
        }
        if (StringUtils.isBlank(blacklistDTO.getReason())) {
            return Result.error("加入原因不能为空");
        }
        if (blacklistDTO.getType() == null) {
            return Result.error("黑名单类型不能为空");
        }
        blacklistService.addToBlacklist(blacklistDTO.getUserId(), blacklistDTO.getReason(), blacklistDTO.getType());
        adminService.logOperation(adminId, "加入黑名单", 
            String.format("将用户%s加入黑名单，原因：%s", blacklistDTO.getUserId(), blacklistDTO.getReason()));
        return Result.success();
    }

    @GetMapping("/blacklist")
    public Result<PageVO<BlacklistVO>> getBlacklist(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum < 1) {
            return Result.error("页码必须大于0");
        }
        if (pageSize < 1) {
            return Result.error("每页条数必须大于0");
        }
        return Result.success(blacklistService.getBlacklist(pageNum, pageSize));
    }
} 