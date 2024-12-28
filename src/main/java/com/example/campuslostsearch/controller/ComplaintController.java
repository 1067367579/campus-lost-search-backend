package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.Result;
import com.example.campuslostsearch.dto.ComplaintDTO;
import com.example.campuslostsearch.dto.ComplaintProcessDTO;
import com.example.campuslostsearch.service.ComplaintService;
import com.example.campuslostsearch.vo.ComplaintVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaint")
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public Result<String> submitComplaint(@RequestHeader("userId") String userId,
                                        @RequestBody ComplaintDTO complaintDTO) {
        if (StringUtils.isBlank(userId)) {
            return Result.error("用户ID不能为空");
        }
        return Result.success(complaintService.submitComplaint(userId, complaintDTO));
    }

    @GetMapping("/list")
    public Result<List<ComplaintVO>> getComplaintList(@RequestHeader("userId") String userId,
                                                    @RequestParam(required = false) Integer status,
                                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return Result.error("用户ID不能为空");
        }
        if (pageNum < 1) {
            return Result.error("页码必须大于0");
        }
        if (pageSize < 1) {
            return Result.error("每页条数必须大于0");
        }
        return Result.success(complaintService.getComplaintList(userId, status, pageNum, pageSize));
    }

    @PostMapping("/{complaintId}/process")
    public Result<?> processComplaint(@PathVariable String complaintId,
                                    @RequestBody ComplaintProcessDTO processDTO) {
        if (StringUtils.isBlank(complaintId)) {
            return Result.error("投诉ID不能为空");
        }
        if (processDTO == null) {
            return Result.error("处理信息不能为空");
        }
        complaintService.processComplaint(complaintId, processDTO);
        return Result.success();
    }
} 