package com.example.campuslostsearch.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintHandleDTO {
    private Long complaintId;
    private String feedback;
    private Long adminId;
    private boolean needPunish;
}
