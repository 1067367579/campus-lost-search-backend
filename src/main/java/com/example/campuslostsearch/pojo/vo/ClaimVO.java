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
public class ClaimVO {
    private Long claimId;
    private Long itemId;
    private String itemName;
    private Integer itemType;
    private String claimType;
    private Long userId;
    private String username;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime handleTime;
    private String handleRemark;
    private String evidence;
}
