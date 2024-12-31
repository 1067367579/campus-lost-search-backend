package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.result.Result;
import com.example.campuslostsearch.pojo.dto.ComplaintDTO;
import com.example.campuslostsearch.pojo.vo.ComplaintCommitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ComplaintController {
    @PostMapping("/complaint")
    public Result<ComplaintCommitVO> commitComplaint(@RequestBody ComplaintDTO complaintDTO) {
        return Result.success();
    }

}
