package com.example.soccer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageDTO implements Serializable {
    //用户姓名
    private String username;

    //用户邮件
    private String email;

//    //开始时间
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private LocalDate startCreateTime;
//
//    //结束时间
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private LocalDate endCreateTime;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;
}
