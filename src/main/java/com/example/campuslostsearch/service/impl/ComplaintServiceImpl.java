package com.example.campuslostsearch.service.impl;

import com.example.campuslostsearch.common.context.BaseContext;
import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.mapper.ClaimMapper;
import com.example.campuslostsearch.mapper.ComplaintMapper;
import com.example.campuslostsearch.mapper.ItemMapper;
import com.example.campuslostsearch.mapper.UserMapper;
import com.example.campuslostsearch.pojo.dto.ComplaintDTO;
import com.example.campuslostsearch.pojo.dto.ComplaintHandleDTO;
import com.example.campuslostsearch.pojo.dto.PageComplaintDTO;
import com.example.campuslostsearch.pojo.entity.Claim;
import com.example.campuslostsearch.pojo.entity.Complaint;
import com.example.campuslostsearch.pojo.entity.Item;
import com.example.campuslostsearch.pojo.entity.User;
import com.example.campuslostsearch.pojo.vo.ComplaintCommitVO;
import com.example.campuslostsearch.pojo.vo.ComplaintDetailVO;
import com.example.campuslostsearch.pojo.vo.ComplaintVO;
import com.example.campuslostsearch.service.ComplaintService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintMapper complaintMapper;

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResult<ComplaintVO> listMyComplaints(Integer pageNum, Integer pageSize, PageComplaintDTO pageComplaintDTO) {
        PageHelper.startPage(pageNum,pageSize);
        List<Complaint> complaintList = complaintMapper.pageQuery(pageComplaintDTO);
        Page<Complaint> page = (Page<Complaint>) complaintList;
        long total = page.getTotal();
        complaintList = page.getResult();
        List<ComplaintVO> complaintVOList = complaintList.parallelStream().map((complaint)->{
            Claim claim = claimMapper.getById(complaint.getClaimId());
            Item item = itemMapper.getItemById(claim.getItemId());
            User user = userMapper.getById(claim.getUserId());
            return ComplaintVO.builder()
                    .claimId(claim.getClaimId())
                    .adminFeedBack(complaint.getAdminFeedback())
                    .claimCreateTime(claim.getCreateTime())
                    .createTime(complaint.getCreateTime())
                    .description(claim.getDescription())
                    .itemName(item.getItemName())
                    .reason(complaint.getReason())
                    .userId(complaint.getUserId())
                    .claimType(claim.getClaimType())
                    .username(user.getUsername())
                    .complaintId(complaint.getComplaintId())
                    .status(complaint.getStatus())
                    .build();
        }).toList();
        return new PageResult<>(total,complaintVOList);
    }

    @Override
    public ComplaintCommitVO commitComplaint(ComplaintDTO complaintDTO) {
        Complaint complaint = Complaint.builder()
                .reason(complaintDTO.getReason())
                .claimId(complaintDTO.getClaimId())
                .userId(BaseContext.getCurrentId())
                .build();
        complaintMapper.save(complaint);
        return ComplaintCommitVO.builder()
                .createTime(LocalDateTime.now())
                .complaintId(complaint.getComplaintId())
                .build();
    }

    @Override
    public void handleComplaint(ComplaintHandleDTO complaintHandleDTO){
        if(complaintHandleDTO.isNeedPunish()) {
            Complaint complaint = complaintMapper.getById(complaintHandleDTO.getComplaintId());
            Claim claim = claimMapper.getById(complaint.getClaimId());
            userMapper.updateUser(User.builder()
                            .status(0)
                            .userId(claim.getUserId())
                    .build());
            claimMapper.handleClaim(Claim.builder()
                            .status(2)
                            .handleRemark("经投诉审核后撤销领回单，请立即还回物品")
                            .claimId(claim.getClaimId())
                    .build());
            return;
        }
        complaintMapper.processComplaint(complaintHandleDTO);
    }

    @Override
    public ComplaintDetailVO getDetail(Long complaintId) {
        return complaintMapper.getDetailById(complaintId);
    }
}
