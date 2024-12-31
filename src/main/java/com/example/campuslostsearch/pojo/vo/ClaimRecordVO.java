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
public class ClaimRecordVO {
    private Long claimId;
    private Long itemId;
    private String username;
    private String description;
    private LocalDateTime createTime;
    private Integer status;
    private LocalDateTime handleTime;
    private String handleRemark;
    private String evidence;
    private String claimType;
}
