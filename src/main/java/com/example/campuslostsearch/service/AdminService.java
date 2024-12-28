package com.example.campuslostsearch.service;

import com.example.campuslostsearch.entity.AdminOperationLog;
import com.example.campuslostsearch.mapper.AdminOperationLogMapper;
import com.example.campuslostsearch.mapper.ClaimMapper;
import com.example.campuslostsearch.mapper.ComplaintMapper;
import com.example.campuslostsearch.vo.AdminOperationLogVO;
import com.example.campuslostsearch.vo.AdminStatsVO;
import com.example.campuslostsearch.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private AdminOperationLogMapper adminOperationLogMapper;
    
    @Autowired
    private ComplaintMapper complaintMapper;
    
    @Autowired
    private ClaimMapper claimMapper;

    public AdminStatsVO getPendingStats() {
        return AdminStatsVO.builder()
                .pendingComplaints(complaintMapper.countPendingComplaints())
                .pendingClaims(claimMapper.countPendingClaims())
                .build();
    }

    public PageVO<AdminOperationLogVO> getOperationLogs(LocalDateTime startTime,
                                                       LocalDateTime endTime,
                                                       Integer pageNum,
                                                       Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<AdminOperationLogVO> logs = adminOperationLogMapper
                .findByTimeRange(startTime, endTime, offset, pageSize)
                .stream()
                .map(this::convertToLogVO)
                .collect(Collectors.toList());
                
        Long total = adminOperationLogMapper.countByTimeRange(startTime, endTime);
        return PageVO.of(logs, total, pageNum, pageSize);
    }

    public void logOperation(String adminId, String operationType, String operationContent) {
        AdminOperationLog log = new AdminOperationLog();
        log.setLogId(UUID.randomUUID().toString().replace("-", ""));
        log.setAdminId(adminId);
        log.setOperationType(operationType);
        log.setOperationContent(operationContent);
        log.setCreateTime(LocalDateTime.now());
        
        adminOperationLogMapper.insert(log);
    }

    private AdminOperationLogVO convertToLogVO(AdminOperationLog log) {
        return AdminOperationLogVO.builder()
                .logId(log.getLogId())
                .adminName(log.getAdminName())
                .operationType(log.getOperationType())
                .operationContent(log.getOperationContent())
                .createTime(log.getCreateTime())
                .build();
    }
} 