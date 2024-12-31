package com.example.campuslostsearch.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimHandleDTO {
    private Integer status;
    private String remark;
    private Long claimId;
}
