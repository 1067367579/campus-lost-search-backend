package com.example.campuslostsearch.service;

import com.example.campuslostsearch.dto.ClaimDTO;
import com.example.campuslostsearch.entity.Claim;
import com.example.campuslostsearch.exception.BusinessException;
import com.example.campuslostsearch.mapper.ClaimMapper;
import com.example.campuslostsearch.vo.ClaimVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClaimService {
    @Autowired
    private ClaimMapper claimMapper;

    @Transactional
    public String submitClaim(String userId, ClaimDTO claimDTO) {
        // 参数验证
        if (StringUtils.isBlank(claimDTO.getItemId())) {
            throw new BusinessException("物品ID不能为空");
        }
        if (claimDTO.getItemType() == null) {
            throw new BusinessException("物品类型不能为空");
        }
        if (StringUtils.isBlank(claimDTO.getDescription())) {
            throw new BusinessException("认领说明不能为空");
        }

        // 业务验证
        List<Claim> existingClaims = claimMapper.findByItemId(claimDTO.getItemId(), claimDTO.getItemType());
        for (Claim claim : existingClaims) {
            if (claim.getUserId().equals(userId) && claim.getStatus() == 0) {
                throw new BusinessException("您已提交过认领申请，请等待处理");
            }
        }
        
        // 处理业务
        Claim claim = new Claim();
        claim.setClaimId(UUID.randomUUID().toString().replace("-", ""));
        claim.setUserId(userId);
        claim.setItemId(claimDTO.getItemId());
        claim.setItemType(claimDTO.getItemType());
        claim.setDescription(claimDTO.getDescription());
        claim.setStatus(0);
        claim.setCreateTime(LocalDateTime.now());
        
        claimMapper.insert(claim);
        return claim.getClaimId();
    }

    public List<ClaimVO> getClaimList(String userId, Integer status, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return claimMapper.findByUserId(userId, status, offset, pageSize)
                .stream()
                .map(this::convertToClaimVO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void processClaim(String claimId, Integer status) {
        Claim claim = claimMapper.findById(claimId);
        if (claim == null) {
            throw new BusinessException("认领申请不存在");
        }
        
        if (claim.getStatus() != 0) {
            throw new BusinessException("该认领申请已处理");
        }
        
        claimMapper.updateStatus(claimId, status);
        
        // 如果通过认领申请，更新物品状态
        if (status == 1) {
            if (claim.getItemType() == 0) {
                claimMapper.updateLostItemStatus(claim.getItemId(), 1);
            } else {
                claimMapper.updateFoundItemStatus(claim.getItemId(), 1);
            }
        }
    }

    private ClaimVO convertToClaimVO(Claim claim) {
        return ClaimVO.builder()
                .claimId(claim.getClaimId())
                .itemId(claim.getItemId())
                .itemName(claim.getItemName())
                .itemType(claim.getItemType())
                .description(claim.getDescription())
                .status(claim.getStatus())
                .createTime(claim.getCreateTime())
                .build();
    }
} 