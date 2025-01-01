package com.example.campuslostsearch.service.impl;

import com.example.campuslostsearch.common.context.BaseContext;
import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.mapper.CategoryMapper;
import com.example.campuslostsearch.mapper.ItemMapper;
import com.example.campuslostsearch.mapper.UserMapper;
import com.example.campuslostsearch.pojo.dto.ItemDTO;
import com.example.campuslostsearch.pojo.dto.PageItemDTO;
import com.example.campuslostsearch.pojo.entity.Category;
import com.example.campuslostsearch.pojo.entity.Item;
import com.example.campuslostsearch.pojo.entity.User;
import com.example.campuslostsearch.pojo.vo.ItemPublishVO;
import com.example.campuslostsearch.pojo.vo.ItemVO;
import com.example.campuslostsearch.pojo.vo.StatisticVO;
import com.example.campuslostsearch.service.ItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public PageResult<ItemVO> listItem(PageItemDTO pageItemDTO) {
        //开启PageHelper插件
        PageHelper.startPage(pageItemDTO.getPageNum(), pageItemDTO.getPageSize());
        log.info("开始分页查询:{}",pageItemDTO);
        List<Item> itemList = itemMapper.pageQuery(pageItemDTO);
        Page<Item> page = (Page<Item>) itemList;
        itemList = page.getResult();
        //对获取到的itemList进行逐个封装VO处理
        List<ItemVO> res = itemList.parallelStream().map((item)-> {
            User user = userMapper.getById(item.getUserId());
            Category category = categoryMapper.getById(item.getCategoryId());
            String categoryName = (category!=null)?category.getName():"类型已禁用";
            ItemVO itemVO = ItemVO.builder()
                    .itemName(item.getItemName())
                    .itemId(item.getItemId())
                    .location(item.getLocation())
                    .status(item.getStatus())
                    .description(item.getDescription())
                    .categoryId(item.getCategoryId())
                    .categoryName(categoryName)
                    .createTime(item.getCreateTime())
                    .images(itemMapper.getImagesByItem(item.getItemId()))
                    .username(user.getUsername())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .itemType(item.getItemType())
                    .build();
            if(item.getItemType() == 0) {
                itemVO.setLostTime(item.getKeyTime());
            } else if(item.getItemType() == 1){
                itemVO.setFoundTime(item.getKeyTime());
            }
            return itemVO;
        }).toList();
        long total = page.getTotal();
        return new PageResult<>(total,res);
    }

    @Override
    @Transactional
    public ItemPublishVO publishItem(ItemDTO itemDTO) {
        Long userId = BaseContext.getCurrentId();
        LocalDateTime keyTime = (itemDTO.getLostTime()!=null)?itemDTO.getLostTime():itemDTO.getFoundTime();
        Item item = Item.builder()
                .itemType(itemDTO.getItemType())
                .keyTime(keyTime)
                .userId(userId)
                .categoryId(itemDTO.getCategoryId())
                .itemName(itemDTO.getItemName())
                .description(itemDTO.getDescription())
                .location(itemDTO.getLocation())
                .build();
        itemMapper.saveItem(item);
        itemMapper.saveImages(itemDTO.getImages(),item.getItemId());
        return ItemPublishVO.builder()
                .itemId(item.getItemId())
                .build();
    }

    @Override
    @Transactional
    public ItemVO getById(Long itemId) {
        List<String> images = itemMapper.getImagesByItem(itemId);
        Item item = itemMapper.getItemById(itemId);
        User user = userMapper.getById(item.getUserId());
        Category category = categoryMapper.getById(item.getCategoryId());
        ItemVO itemVO = ItemVO.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .location(item.getLocation())
                .status(item.getStatus())
                .description(item.getDescription())
                .categoryId(item.getCategoryId())
                .images(images)
                .createTime(item.getCreateTime())
                .categoryName(category.getName())
                .foundTime(item.getKeyTime())
                .itemType(item.getItemType())
                .username(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
        if(item.getItemType() == 0) {
            itemVO.setLostTime(item.getKeyTime());
        } else {
            itemVO.setFoundTime(item.getKeyTime());
        }
        return itemVO;
    }

    @Override
    @Transactional
    public void updateItem(ItemDTO itemDTO) {
        itemMapper.deleteById(itemDTO.getItemId());
        this.publishItem(itemDTO);
    }

    @Override
    @Transactional
    public StatisticVO getItemStats() {
        return StatisticVO.builder()
                .lostItems(itemMapper.getLostCount())
                .foundItems(itemMapper.getFoundCount())
                .build();
    }
}
