package com.example.campuslostsearch.service;

import com.example.campuslostsearch.entity.Blacklist;
import com.example.campuslostsearch.mapper.BlacklistMapper;
import com.example.campuslostsearch.mapper.UserMapper;
import com.example.campuslostsearch.vo.BlacklistVO;
import com.example.campuslostsearch.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BlacklistService {
    @Autowired
    private BlacklistMapper blacklistMapper;
    
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void addToBlacklist(String userId, String reason, Integer type) {
        Blacklist blacklist = new Blacklist();
        blacklist.setBlacklistId(UUID.randomUUID().toString().replace("-", ""));
        blacklist.setUserId(userId);
        blacklist.setReason(reason);
        blacklist.setType(type);
        blacklist.setCreateTime(LocalDateTime.now());
        
        blacklistMapper.insert(blacklist);
        // 冻结用户账号
        userMapper.updateStatus(userId, 0);
    }

    public PageVO<BlacklistVO> getBlacklist(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Blacklist> blacklist = blacklistMapper.findAll(offset, pageSize);
        
        List<BlacklistVO> voList = blacklist.stream()
                .map(item -> BlacklistVO.builder()
                        .blacklistId(item.getBlacklistId())
                        .userId(item.getUserId())
                        .username(item.getUsername())
                        .reason(item.getReason())
                        .type(item.getType())
                        .createTime(item.getCreateTime())
                        .build())
                .collect(Collectors.toList());
                
        Long total = blacklistMapper.countAll();
        return PageVO.of(voList, total, pageNum, pageSize);
    }
} 