package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.Result;
import com.example.campuslostsearch.dto.ClaimDTO;
import com.example.campuslostsearch.service.ClaimService;
import com.example.campuslostsearch.vo.ClaimVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claim")
public class ClaimController {
    @Autowired
    private ClaimService claimService;

    @PostMapping
    public Result<String> submitClaim(@RequestHeader("userId") String userId,
                                    @RequestBody ClaimDTO claimDTO) {
        if (StringUtils.isBlank(userId)) {
            return Result.error("用户ID不能为空");
        }
        return Result.success(claimService.submitClaim(userId, claimDTO));
    }

    @GetMapping("/list")
    public Result<List<ClaimVO>> getClaimList(@RequestHeader("userId") String userId,
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
        return Result.success(claimService.getClaimList(userId, status, pageNum, pageSize));
    }

    @PostMapping("/{claimId}/process")
    public Result<?> processClaim(@PathVariable String claimId,
                                @RequestParam Integer status) {
        if (StringUtils.isBlank(claimId)) {
            return Result.error("认领ID不能为空");
        }
        if (status == null) {
            return Result.error("状态不能为空");
        }
        if (status != 1 && status != 2) {
            return Result.error("状态值无效");
        }
        claimService.processClaim(claimId, status);
        return Result.success();
    }
} 