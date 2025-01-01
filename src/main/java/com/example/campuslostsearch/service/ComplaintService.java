package com.example.campuslostsearch.service;

import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.pojo.dto.ComplaintDTO;
import com.example.campuslostsearch.pojo.dto.ComplaintHandleDTO;
import com.example.campuslostsearch.pojo.dto.PageComplaintDTO;
import com.example.campuslostsearch.pojo.vo.ComplaintCommitVO;
import com.example.campuslostsearch.pojo.vo.ComplaintDetailVO;
import com.example.campuslostsearch.pojo.vo.ComplaintVO;

public interface ComplaintService {
    PageResult<ComplaintVO> listMyComplaints(Integer pageNum, Integer pageSize, PageComplaintDTO pageComplaintDTO);

    ComplaintCommitVO commitComplaint(ComplaintDTO complaintDTO);

    void handleComplaint(ComplaintHandleDTO complaintHandleDTO);

    ComplaintDetailVO getDetail(Long complaintId);
}
