package com.example.campuslostsearch.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfo {
    private String itemId;
    private String itemName;
    private Integer itemType;
}
