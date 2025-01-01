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
public class BlackListVO {
    private Long blacklistId;
    private Long userId;
    private String reason;
    private Integer type;
    private LocalDateTime createTime;
    private Long adminId;
    private String username;
    private String operatorName;
    private String blacklistCreateTime;
    private String phone;
    private String email;
    private Integer status;
}

