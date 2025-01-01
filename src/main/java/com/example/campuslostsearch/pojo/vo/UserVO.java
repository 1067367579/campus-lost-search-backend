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
public class UserVO {
    private String username;
    private Long userId;
    private LocalDateTime createTime;
    private Integer status;
    private String idNumber;
    private String phone;
    private String email;
    private boolean inBlackList;
}
