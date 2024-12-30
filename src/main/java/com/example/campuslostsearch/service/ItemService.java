package com.example.campuslostsearch.service;

import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.pojo.dto.ItemDTO;
import com.example.campuslostsearch.pojo.dto.PageItemDTO;
import com.example.campuslostsearch.pojo.vo.ItemPublishVO;
import com.example.campuslostsearch.pojo.vo.ItemVO;

public interface ItemService {
    PageResult<ItemVO> listItem(PageItemDTO build);

    ItemPublishVO publishItem(ItemDTO itemDTO);

    ItemVO getById(Long itemId);

    void updateItem(ItemDTO itemDTO);

}
