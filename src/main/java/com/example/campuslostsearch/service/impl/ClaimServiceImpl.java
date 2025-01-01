package com.example.campuslostsearch.service.impl;

import com.example.campuslostsearch.common.context.BaseContext;
import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.mapper.CategoryMapper;
import com.example.campuslostsearch.mapper.ClaimMapper;
import com.example.campuslostsearch.mapper.ItemMapper;
import com.example.campuslostsearch.mapper.UserMapper;
import com.example.campuslostsearch.pojo.dto.ClaimDTO;
import com.example.campuslostsearch.pojo.dto.ClaimHandleDTO;
import com.example.campuslostsearch.pojo.entity.Category;
import com.example.campuslostsearch.pojo.entity.Claim;
import com.example.campuslostsearch.pojo.entity.Item;
import com.example.campuslostsearch.pojo.entity.User;
import com.example.campuslostsearch.pojo.vo.ClaimDetailVO;
import com.example.campuslostsearch.pojo.vo.ClaimRecordVO;
import com.example.campuslostsearch.pojo.vo.ClaimVO;
import com.example.campuslostsearch.service.ClaimService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Long commitClaim(ClaimDTO claimDTO) {
        Claim claim = Claim.builder()
                .description(claimDTO.getDescription())
                .itemId(claimDTO.getItemId())
                .itemType(claimDTO.getItemType())
                .userId(BaseContext.getCurrentId())
                .claimType(claimDTO.getClaimType())
                .evidence(claimDTO.getEvidence())
                .build();
        claimMapper.save(claim);
        return claim.getClaimId();
    }

    @Override
    public PageResult<ClaimVO> listClaim(Integer pageNum, Integer pageSize,Long userId, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        Claim claimQuery = Claim.builder()
                .status(status)
                .userId(userId)
                .build();
        List<Claim> list = claimMapper.pageQuery(claimQuery);
        Page<Claim> page = (Page<Claim>) list;
        list = page.getResult();
        //加工处理
        List<ClaimVO> res = list.parallelStream().map((claim)-> {
            Long itemId = claim.getItemId();
            Item item = itemMapper.getItemById(itemId);
            return ClaimVO.builder()
                    .claimId(claim.getClaimId())
                    .description(claim.getDescription())
                    .itemId(claim.getItemId())
                    .userId(claim.getUserId())
                    .itemType(claim.getItemType())
                    .itemName(item.getItemName())
                    .status(claim.getStatus())
                    .evidence(claim.getEvidence())
                    .claimType(claim.getClaimType())
                    .createTime(claim.getCreateTime())
                    .handleTime(claim.getHandleTime())
                    .handleRemark(claim.getHandleRemark())
                    .build();
        }).toList();
        long total = page.getTotal();
        return new PageResult<>(total,res);
    }

    @Override
    public List<ClaimRecordVO> claimRecord(Long itemId) {
        List<Claim> claims = claimMapper.getByItemId(itemId);
        return claims.parallelStream().map((claim)->{
            User user = userMapper.getById(claim.getUserId());
            return ClaimRecordVO.builder()
                    .claimId(claim.getClaimId())
                    .description(claim.getDescription())
                    .username(user.getUsername())
                    .createTime(claim.getCreateTime())
                    .handleTime(claim.getHandleTime())
                    .evidence(claim.getEvidence())
                    .claimType(claim.getClaimType())
                    .handleRemark(claim.getHandleRemark())
                    .status(claim.getStatus())
                    .build();
        }).toList();
    }

    @Override
    @Transactional
    public ClaimDetailVO getDetail(Long claimId) {
        // 首先获取 Claim 对象（必须串行）
        Claim claim = claimMapper.getById(claimId);
        return getClaimDetailVO(claim);
    }

    private ClaimDetailVO getClaimDetailVO(Claim claim) {
        Long itemId = claim.getItemId();
        Long userId = claim.getUserId();
        // 并行获取 Item 和 Images
        CompletableFuture<Item> itemFuture = CompletableFuture.supplyAsync(() -> itemMapper.getItemById(itemId));
        CompletableFuture<List<String>> imagesFuture = CompletableFuture.supplyAsync(() -> itemMapper.getImagesByItem(itemId));
        CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(() -> userMapper.getById(userId));
        // 串行获取 Category，依赖于 Item 的结果
        CompletableFuture<Category> categoryFuture = itemFuture.thenApply(item -> categoryMapper.getById(item.getCategoryId()));
        // 等待所有任务完成
        CompletableFuture<Void> allOf = CompletableFuture.allOf(itemFuture, imagesFuture, categoryFuture,userFuture);
        // 合并结果并返回
        return allOf.thenApply(v -> {
            try {
                // 获取结果
                Item item = itemFuture.get();
                List<String> images = imagesFuture.get();
                Category category = categoryFuture.get();
                User user = userFuture.get();
                // 构建并返回 ClaimDetailVO
                return ClaimDetailVO.builder()
                        .claimId(claim.getClaimId())
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .description(claim.getDescription())
                        .itemName(item.getItemName())
                        .itemId(item.getItemId())
                        .itemType(item.getItemType())
                        .categoryName(category.getName())
                        .createTime(claim.getCreateTime())
                        .claimType(claim.getClaimType())
                        .evidence(claim.getEvidence())
                        .foundTime(claim.getHandleTime())
                        .images(images)
                        .itemDescription(item.getDescription())
                        .location(item.getLocation())
                        .lostTime(item.getKeyTime())
                        .handleRemark(claim.getHandleRemark())
                        .handleTime(claim.getHandleTime())
                        .status(claim.getStatus())
                        .foundTime(item.getKeyTime())
                        .build();
            } catch (Exception e) {
                throw new RuntimeException("Error while fetching claim details", e);
            }
        }).join();
    }

    @Override
    public void handleClaim(ClaimHandleDTO claimHandleDTO) {
        claimMapper.handleClaim(Claim.builder()
                        .handleRemark(claimHandleDTO.getRemark())
                        .claimId(claimHandleDTO.getClaimId())
                        .status(claimHandleDTO.getStatus())
                .build());
    }

    @Override
    public PageResult<ClaimDetailVO> adminQuery(Integer pageNum, Integer pageSize, Integer status, String claimType) {
        PageHelper.startPage(pageNum, pageSize);
        Claim claimQuery = Claim.builder()
                .status(status)
                .claimType(claimType)
                .build();
        List<Claim> claimList = claimMapper.pageQuery(claimQuery);
        Page<Claim> page = (Page<Claim>) claimList;
        claimList = page.getResult();
        long total = page.getTotal();
        List<ClaimDetailVO> claimDetailVOList = claimList.parallelStream().map(this::getClaimDetailVO).toList();
        return new PageResult<>(total,claimDetailVOList);
    }
}
