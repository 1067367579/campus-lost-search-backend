package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.context.BaseContext;
import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.common.result.Result;
import com.example.campuslostsearch.pojo.dto.ItemDTO;
import com.example.campuslostsearch.pojo.dto.PageItemDTO;
import com.example.campuslostsearch.pojo.vo.ItemPublishVO;
import com.example.campuslostsearch.pojo.vo.ItemVO;
import com.example.campuslostsearch.pojo.vo.StatisticVO;
import com.example.campuslostsearch.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;

    //发布丢失物品
    @PostMapping("/lost-item")
    public Result<ItemPublishVO> publishLostItem(@RequestBody ItemDTO itemDTO) {
        log.info("发布丢失物品：{}", itemDTO);
        itemDTO.setItemType(0);
        ItemPublishVO itemPublishVO = itemService.publishItem(itemDTO);
        return Result.success(itemPublishVO);
    }

    @PutMapping("/lost-item")
    public Result<Object> updateLostItem(@RequestBody ItemDTO itemDTO) {
        log.info("修改丢失物品：{}",itemDTO);
        itemDTO.setItemType(0);
        itemService.updateItem(itemDTO);
        return Result.success();
    }

    @GetMapping("/recent-items")
    public Result<PageResult<ItemVO>> recentItems(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        log.info("获取最新物品：{},{}",pageNum,pageSize);
        return Result.success(itemService.listItem(PageItemDTO.builder()
                .status(0)
                .pageSize(pageSize)
                .pageNum(pageNum)
                .build()));
    }

    @GetMapping("/item/my-items")
    public Result<PageResult<ItemVO>> getMyItems(@RequestParam Integer pageNum,
                                                 @RequestParam Integer pageSize,
                                                 @RequestParam(required = false) Integer itemType,
                                                 @RequestParam(required = false) Integer categoryId,
                                                 @RequestParam(required = false) Integer status ){
        log.info("查看我的物品：{},{},{}",pageNum,pageSize,itemType);
        return Result.success(itemService.listItem(PageItemDTO.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .itemType(itemType)
                        .status(status)
                        .categoryId(categoryId)
                        .userId(BaseContext.getCurrentId())
                .build()));
    }

    @GetMapping("/stats")
    public Result<StatisticVO> getItemStats() {
        log.info("查看物品统计数据");
        StatisticVO statisticVO = itemService.getItemStats();
        return Result.success(statisticVO);
    }



    //根据itemId查找物品
    @GetMapping("/item/{itemId}")
    public Result<ItemVO> getItemById(@PathVariable Long itemId) {
        log.info("根据Id查询物品：{}",itemId);
        ItemVO itemVO = itemService.getById(itemId);
        return Result.success(itemVO);
    }

    //获取丢失物品列表
    @GetMapping("/lost-item/list")
    public Result<PageResult<ItemVO>> listLostItem(@RequestParam Integer pageNum,
                                                       @RequestParam Integer pageSize,
                                                       @RequestParam(required = false) Integer categoryId,
                                                       @RequestParam(required = false) String keyword,
                                                       @RequestParam(required = false) Integer status) {
        log.info("分页查询丢失物品列表，页码：{},页大小：{},分类id：{},关键字：{},状态：{}",pageNum,pageSize,categoryId,keyword,status);
        return Result.success(itemService.listItem(PageItemDTO.builder()
                        .pageNum(pageNum)
                        .pageSize(pageSize)
                        .categoryId(categoryId)
                        .keyword(keyword)
                        .status(status)
                        .itemType(0)
                .build()));
    }

    //发布拾取物品
    @PostMapping("/found-item")
    public Result<ItemPublishVO> publishFoundItem(@RequestBody ItemDTO itemDTO) {
        log.info("发布拾取物品：{}", itemDTO);
        itemDTO.setItemType(1);
        ItemPublishVO itemPublishVO = itemService.publishItem(itemDTO);
        return Result.success(itemPublishVO);
    }

    //获取拾取物品列表
    @GetMapping("/found-item/list")
    public Result<PageResult<ItemVO>> listFoundItem(@RequestParam Integer pageNum,
                                                    @RequestParam Integer pageSize,
                                                    @RequestParam(required = false) Integer categoryId,
                                                    @RequestParam(required = false) String keyword,
                                                    @RequestParam(required = false) Integer status) {
        log.info("分页查询丢失物品列表，页码：{},页大小：{},分类id：{},关键字：{},状态：{}", pageNum, pageSize, categoryId, keyword, status);
        return Result.success(itemService.listItem(PageItemDTO.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .categoryId(categoryId)
                .keyword(keyword)
                .status(status)
                .itemType(1)
                .build()));
    }
}
