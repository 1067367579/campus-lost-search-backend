package com.example.campuslostsearch.service;

import com.example.campuslostsearch.dto.ItemDTO;
import com.example.campuslostsearch.entity.FoundItem;
import com.example.campuslostsearch.entity.LostItem;
import com.example.campuslostsearch.exception.BusinessException;
import com.example.campuslostsearch.mapper.ItemMapper;
import com.example.campuslostsearch.vo.ItemVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemMapper itemMapper;

    public String publishLostItem(String userId, ItemDTO itemDTO) {
        // 参数验证
        validateItemDTO(itemDTO);
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户ID不能为空");
        }

        // 处理业务
        LostItem item = new LostItem();
        item.setLostItemId(UUID.randomUUID().toString().replace("-", ""));
        item.setUserId(userId);
        item.setCategoryId(itemDTO.getCategoryId());
        item.setItemName(itemDTO.getItemName());
        item.setDescription(itemDTO.getDescription());
        item.setLocation(itemDTO.getLocation());
        item.setLostTime(itemDTO.getTime());
        item.setStatus(0);
        item.setCreateTime(LocalDateTime.now());
        
        itemMapper.insertLostItem(item);
        return item.getLostItemId();
    }

    public String publishFoundItem(String userId, ItemDTO itemDTO) {
        // 参数验证
        validateItemDTO(itemDTO);
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户ID不能为空");
        }

        // 处理业务
        FoundItem item = new FoundItem();
        item.setFoundItemId(UUID.randomUUID().toString().replace("-", ""));
        item.setUserId(userId);
        item.setCategoryId(itemDTO.getCategoryId());
        item.setItemName(itemDTO.getItemName());
        item.setDescription(itemDTO.getDescription());
        item.setLocation(itemDTO.getLocation());
        item.setFoundTime(itemDTO.getTime());
        item.setStatus(0);
        item.setCreateTime(LocalDateTime.now());
        
        itemMapper.insertFoundItem(item);
        return item.getFoundItemId();
    }

    public List<ItemVO> findLostItems(String categoryId, String keyword, 
                                    Integer status, Integer pageNum, Integer pageSize) {
        // 参数验证
        if (pageNum == null || pageNum < 1) {
            throw new BusinessException("页码必须大于0");
        }
        if (pageSize == null || pageSize < 1) {
            throw new BusinessException("每页条数必须大于0");
        }
        
        int offset = (pageNum - 1) * pageSize;
        return itemMapper.findLostItems(categoryId, keyword, status, offset, pageSize)
                .stream()
                .map(this::convertToItemVO)
                .collect(Collectors.toList());
    }

    public List<ItemVO> findFoundItems(String categoryId, String keyword,
                                     Integer status, Integer pageNum, Integer pageSize) {
        // 参数验证
        if (pageNum == null || pageNum < 1) {
            throw new BusinessException("页码必须大于0");
        }
        if (pageSize == null || pageSize < 1) {
            throw new BusinessException("每页条数必须大于0");
        }
        
        int offset = (pageNum - 1) * pageSize;
        return itemMapper.findFoundItems(categoryId, keyword, status, offset, pageSize)
                .stream()
                .map(this::convertToItemVO)
                .collect(Collectors.toList());
    }

    private ItemVO convertToItemVO(Object item) {
        if (item instanceof LostItem lostItem) {
            return ItemVO.builder()
                    .itemId(lostItem.getLostItemId())
                    .itemName(lostItem.getItemName())
                    .categoryName(lostItem.getCategoryName())
                    .description(lostItem.getDescription())
                    .location(lostItem.getLocation())
                    .time(lostItem.getLostTime())
                    .status(lostItem.getStatus())
                    .publisherId(lostItem.getUserId())
                    .publisherName(lostItem.getUsername())
                    .build();
        } else {
            FoundItem foundItem = (FoundItem) item;
            return ItemVO.builder()
                    .itemId(foundItem.getFoundItemId())
                    .itemName(foundItem.getItemName())
                    .categoryName(foundItem.getCategoryName())
                    .description(foundItem.getDescription())
                    .location(foundItem.getLocation())
                    .time(foundItem.getFoundTime())
                    .status(foundItem.getStatus())
                    .publisherId(foundItem.getUserId())
                    .publisherName(foundItem.getUsername())
                    .build();
        }
    }

    private void validateItemDTO(ItemDTO itemDTO) {
        if (StringUtils.isBlank(itemDTO.getItemName())) {
            throw new BusinessException("物品名称不能为空");
        }
        if (StringUtils.isBlank(itemDTO.getCategoryId())) {
            throw new BusinessException("物品类别不能为空");
        }
        if (StringUtils.isBlank(itemDTO.getLocation())) {
            throw new BusinessException("地点不能为空");
        }
        if (itemDTO.getTime() == null) {
            throw new BusinessException("时间不能为空");
        }
        if (itemDTO.getTime().isAfter(LocalDateTime.now())) {
            throw new BusinessException("时间不能晚于当前时间");
        }
    }
} 