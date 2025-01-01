package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.result.PageResult;
import com.example.campuslostsearch.common.result.Result;
import com.example.campuslostsearch.mapper.BlackListMapper;
import com.example.campuslostsearch.pojo.vo.BlackListVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class BlackListController {

    @Autowired
    private BlackListMapper blackListMapper;

    @GetMapping("/admin/blacklist")
    public Result<PageResult<BlackListVO>> listBlackList(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                                         @RequestParam(required = false) String username) {
        log.info("查询黑名单视图:{}",username);
        PageHelper.startPage(pageNum, pageSize);
        List<BlackListVO> blackListVOList =  blackListMapper.getBlacklistView(username);
        Page<BlackListVO> page = (Page<BlackListVO>) blackListVOList;
        return Result.success(new PageResult<>(page.getTotal(),page.getResult()));
    }

    @GetMapping("/admin/blacklist/{blacklistId}")
    public Result<BlackListVO> getBlackListDetail(@PathVariable Integer blacklistId) {
        log.info("查询用户加入黑名单详情：{}",blacklistId);
        return Result.success(blackListMapper.getDetailById(blacklistId));
    }

}
