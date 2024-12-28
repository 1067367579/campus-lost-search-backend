package com.example.campuslostsearch.service;

import com.example.campuslostsearch.dto.ComplaintDTO;
import com.example.campuslostsearch.dto.ComplaintProcessDTO;
import com.example.campuslostsearch.entity.Complaint;
import com.example.campuslostsearch.exception.BusinessException;
import com.example.campuslostsearch.mapper.ComplaintMapper;
import com.example.campuslostsearch.vo.ComplaintVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComplaintService {
    @Autowired
    private ComplaintMapper complaintMapper;
    
    @Autowired
    private BlacklistService blacklistService;

    @Transactional
    public String submitComplaint(String userId, ComplaintDTO complaintDTO) {
        // 参数验证
        if (StringUtils.isBlank(complaintDTO.getClaimId())) {
            throw new BusinessException("认领ID不能为空");
        }
        if (StringUtils.isBlank(complaintDTO.getCategory())) {
            throw new BusinessException("投诉类别不能为空");
        }
        if (StringUtils.isBlank(complaintDTO.getReason())) {
            throw new BusinessException("投诉原因不能为空");
        }
        
        // 业务验证
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        Long complaintCount = complaintMapper.countByUserIdWithinPeriod(userId, thirtyDaysAgo);
        if (complaintCount >= 5) {
            throw new BusinessException("您30天内的投诉次数已达上限");
        }
        
        Complaint complaint = new Complaint();
        complaint.setComplaintId(UUID.randomUUID().toString().replace("-", ""));
        complaint.setUserId(userId);
        complaint.setClaimId(complaintDTO.getClaimId());
        complaint.setCategory(complaintDTO.getCategory());
        complaint.setReason(complaintDTO.getReason());
        complaint.setStatus(0);
        complaint.setCreateTime(LocalDateTime.now());
        
        complaintMapper.insert(complaint);
        return complaint.getComplaintId();
    }

    public List<ComplaintVO> getComplaintList(String userId, Integer status, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return complaintMapper.findByUserId(userId, status, offset, pageSize)
                .stream()
                .map(this::convertToComplaintVO)
                .collect(Collectors.toList());
    }

    public List<ComplaintVO> getPendingComplaints(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return complaintMapper.findPendingComplaints(offset, pageSize)
                .stream()
                .map(this::convertToComplaintVO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void processComplaint(String complaintId, ComplaintProcessDTO processDTO) {
        // 参数验证
        if (StringUtils.isBlank(complaintId)) {
            throw new BusinessException("投诉ID不能为空");
        }
        if (StringUtils.isBlank(processDTO.getAdminFeedback())) {
            throw new BusinessException("处理意见不能为空");
        }
        
        // 业务验证
        Complaint complaint = complaintMapper.findById(complaintId);
        if (complaint == null) {
            throw new BusinessException("投诉不存在");
        }
        if (complaint.getStatus() != 0) {
            throw new BusinessException("该投诉已处理");
        }
        
        complaintMapper.updateStatus(complaintId, processDTO.getAdminFeedback());
        
        // 检查用户被投诉次数，如果超过3次，加入黑名单
        String userId = complaint.getUserId();
        Long complaintCount = complaintMapper.countByUserId(userId, null);
        if (complaintCount >= 3) {
            blacklistService.addToBlacklist(userId, "多次被投诉", 1);
        }
    }

    private ComplaintVO convertToComplaintVO(Complaint complaint) {
        return ComplaintVO.builder()
                .complaintId(complaint.getComplaintId())
                .category(complaint.getCategory())
                .reason(complaint.getReason())
                .adminFeedback(complaint.getAdminFeedback())
                .status(complaint.getStatus())
                .createTime(complaint.getCreateTime())
                .complainantId(complaint.getUserId())
                .complainantName(complaint.getComplainantName())
                .claimId(complaint.getClaimId())
                .claimDescription(complaint.getClaimDescription())
                .itemId(complaint.getItemId())
                .itemName(complaint.getItemName())
                .itemType(complaint.getItemType())
                .build();
    }
} 