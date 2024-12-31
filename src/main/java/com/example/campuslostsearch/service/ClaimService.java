package com.example.campuslostsearch.service;

import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.pojo.dto.ClaimDTO;
import com.example.campuslostsearch.pojo.dto.ClaimHandleDTO;
import com.example.campuslostsearch.pojo.vo.ClaimDetailVO;
import com.example.campuslostsearch.pojo.vo.ClaimRecordVO;
import com.example.campuslostsearch.pojo.vo.ClaimVO;

import java.util.List;

public interface ClaimService {
    Long commitClaim(ClaimDTO claimDTO);

    PageResult<ClaimVO> listClaim(Integer pageNum, Integer pageSize,Long userId,Integer status);

    List<ClaimRecordVO> claimRecord(Long itemId);

    ClaimDetailVO getDetail(Long claimId);

    void handleClaim(ClaimHandleDTO claimHandleDTO);

    PageResult<ClaimDetailVO> adminQuery(Integer pageNum, Integer pageSize, Integer status, String claimType);
}
