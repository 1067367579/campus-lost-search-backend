package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.context.BaseContext;
import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.common.result.Result;
import com.example.campuslostsearch.pojo.dto.ClaimDTO;
import com.example.campuslostsearch.pojo.dto.ClaimHandleDTO;
import com.example.campuslostsearch.pojo.vo.ClaimCommitVO;
import com.example.campuslostsearch.pojo.vo.ClaimDetailVO;
import com.example.campuslostsearch.pojo.vo.ClaimRecordVO;
import com.example.campuslostsearch.pojo.vo.ClaimVO;
import com.example.campuslostsearch.service.ClaimService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping("/claim")
    public Result<ClaimCommitVO> commitClaim(@RequestBody ClaimDTO claimDTO) {
        log.info("提交认领单：{}",claimDTO);
        Long claimId = claimService.commitClaim(claimDTO);
        return Result.success(ClaimCommitVO.builder()
                        .claimId(claimId)
                .build());
    }

    @GetMapping("/claim/list")
    public Result<PageResult<ClaimVO>> listClaim(@RequestParam Integer pageNum,
                                                       @RequestParam Integer pageSize) {
        log.info("分页查询认领列表，页码：{}，页大小：{}",pageNum,pageSize);
        Long userId = BaseContext.getCurrentId();
        return Result.success(claimService.listClaim(pageNum,pageSize,userId,null));
    }

    //获取物品的认领记录
    @GetMapping("/item/{itemId}/claims")
    public Result<List<ClaimRecordVO>> claimRecord(@PathVariable Long itemId) {
        log.info("获取物品Id为：{}的认领记录",itemId);
        List<ClaimRecordVO> list = claimService.claimRecord(itemId);
        return Result.success(list);
    }

    //获取认领/还回申请详情
    @GetMapping("/claim/{claimId}")
    public Result<ClaimDetailVO> getDetail(@PathVariable Long claimId) {
        log.info("获取认领/换回申请详情:{}",claimId);
        return Result.success(claimService.getDetail(claimId));
    }

    //处理认领/还回申请
    @PutMapping("admin/claims/{claimId}/status")
    public Result<Object> handleClaim(@RequestBody ClaimHandleDTO claimHandleDTO, @PathVariable Long claimId) {
        log.info("管理员处理认领/换回申请:{},{}",claimHandleDTO,claimId);
        claimHandleDTO.setClaimId(claimId);
        claimService.handleClaim(claimHandleDTO);
        return Result.success();
    }

    //管理员查看所有的认领单
    @GetMapping("/admin/claims")
    public Result<PageResult<ClaimDetailVO>> adminQuery(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize,
                                          @RequestParam(required = false) Integer status,
                                            @RequestParam(required = false) String claimType) {
        log.info("管理员查看所有认领单:{},{},{},{}",pageNum,pageSize,status,claimType);
        return Result.success(claimService.adminQuery(pageNum,pageSize,status,claimType));
    }
}
