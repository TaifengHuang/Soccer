package com.example.soccer.controller;

import lombok.extern.slf4j.Slf4j;
import com.example.soccer.pojo.Result;
import com.example.soccer.utils.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j  //日志注解
@RestController
public class UploadController {
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {

        log.info("文件上传，文件名:{}", image.getOriginalFilename());

        //调用阿里云OSS工具类，将上传上来的文件存入阿里云
        String url = aliOSSUtils.upload(image);

        //将图片上传完成后的url返回，用于浏览器回显展示
        return Result.success(url);
    }
}
