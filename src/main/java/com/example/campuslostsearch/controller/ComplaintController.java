package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.context.BaseContext;
import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.common.result.Result;
import com.example.campuslostsearch.pojo.dto.ComplaintDTO;
import com.example.campuslostsearch.pojo.dto.ComplaintHandleDTO;
import com.example.campuslostsearch.pojo.dto.PageComplaintDTO;
import com.example.campuslostsearch.pojo.vo.ComplaintCommitVO;
import com.example.campuslostsearch.pojo.vo.ComplaintDetailVO;
import com.example.campuslostsearch.pojo.vo.ComplaintVO;
import com.example.campuslostsearch.service.ComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    //提交投诉
    @PostMapping("/complaint")
    public Result<ComplaintCommitVO> commitComplaint(@RequestBody ComplaintDTO complaintDTO) {
        log.info("提交投诉：{}",complaintDTO);
        ComplaintCommitVO complaintCommitVO = complaintService.commitComplaint(complaintDTO);
        return Result.success(complaintCommitVO);
    }

    //获取我的投诉
    @GetMapping("/complaint/my-list")
    public Result<PageResult<ComplaintVO>> listMyComplaints(@RequestParam Integer pageNum,
                                                            @RequestParam Integer pageSize,
                                                            @RequestParam(required = false) Integer status ){
        log.info("查看我的投诉列表:{}，{}，{}",pageNum,pageSize,status);
        PageComplaintDTO pageComplaintDTO = PageComplaintDTO.builder()
                .status(status)
                .userId(BaseContext.getCurrentId())
                .build();
        return Result.success(complaintService.listMyComplaints(pageNum,pageSize,pageComplaintDTO));
    }

    //管理员获取投诉列表
    @GetMapping("/admin/complaints")
    public Result<PageResult<ComplaintVO>> listComplaints(@RequestParam Integer pageNum,
                                                          @RequestParam Integer pageSize,
                                                          @RequestParam(required = false) Integer status ) {
        log.info("管理员获取投诉列表:{},{},{}",pageNum,pageSize,status);
        PageComplaintDTO pageComplaintDTO = PageComplaintDTO.builder()
                .status(status)
                .build();
        return Result.success(complaintService.listMyComplaints(pageNum,pageSize,pageComplaintDTO));
    }

    //管理员处理投诉
    @PutMapping("/admin/complaints/{complaintId}")
    public Result<Object> handleComplaint(@PathVariable Long complaintId,
                                          @RequestBody ComplaintHandleDTO complaintHandleDTO) {
        log.info("管理员处理投诉:{}, {}",complaintId,complaintHandleDTO);
        complaintHandleDTO.setComplaintId(complaintId);
        complaintHandleDTO.setAdminId(BaseContext.getCurrentId());
        complaintService.handleComplaint(complaintHandleDTO);
        return Result.success();
    }

    @GetMapping("/complaint/{complaintId}")
    public Result<ComplaintDetailVO> getComplaintDetail(@PathVariable Long complaintId) {
        log.info("查询投诉详情:{}",complaintId);
        return Result.success(complaintService.getDetail(complaintId));
    }

}
