package com.example.campuslostsearch.controller;

import com.example.campuslostsearch.common.result.Result;
import com.example.campuslostsearch.common.utils.AliOssUtil;
import com.example.campuslostsearch.pojo.vo.UploadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload/image")
    public Result<UploadVO> upload(MultipartFile file) {
        log.info("上传文件：{}", file);

        //阿里云oss要配置自己的bucket
        try {
            String filename = file.getOriginalFilename();
            //截取原始文件名的后缀
            String extension = filename.substring(filename.lastIndexOf("."));
            String objectName = UUID.randomUUID() + extension;
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            log.info("文件路径：{}", filePath);
            return Result.success(UploadVO.builder()
                            .url(filePath)
                            .build());
        } catch (IOException e) {
            log.info("文件上传失败");
        }
        return Result.error("文件上传失败！");
    }
}
