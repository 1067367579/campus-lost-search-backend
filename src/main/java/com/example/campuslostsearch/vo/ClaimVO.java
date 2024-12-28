package com.example.campuslostsearch.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClaimVO {
    private String claimId;
    private String itemId;
    private String itemName;
    private Integer itemType;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
} 