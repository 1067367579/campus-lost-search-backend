package com.example.campuslostsearch.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBlackListVO {
    private Long userId;
    private String username;
    private String phone;
    private String email;
    private Integer status;
    private LocalDateTime createTime;
    private boolean inBlacklist;
    private Integer type;
    private String reason;
    private LocalDateTime blacklistCreateTime;
    private String operatorName;
}
