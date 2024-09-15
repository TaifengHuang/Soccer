package com.example.soccer.service.impl;

import com.example.soccer.dto.UserPageDTO;
import com.example.soccer.mapper.UserMapper;
import com.example.soccer.pojo.PageResult;
import com.example.soccer.pojo.Result;
import com.example.soccer.pojo.User;
import com.example.soccer.service.UserService;
import com.example.soccer.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.scanner.Constant;

import com.example.soccer.exception.AccountNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.soccer.constant.MessageConstant;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {

        User userIsExist = userMapper.findUserIsExist(user);
        if (userIsExist != null) {
            //账号已存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_FOUND);
        }
        if (user.getUsername().length() < 3 || user.getUsername().length() > 10) {
            //账号格式错误
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_FORMAT_ERROR);
        }
        if (user.getPassword().length() < 3 || user.getPassword().length() > 10) {
            //密码格式错误
            throw new AccountNotFoundException(MessageConstant.PASSWORD_FORMAT_ERROR);
        }

        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        // 对密码进行md5加密
        String md5String = Md5Util.getMD5String(user.getPassword());
        user.setPassword(md5String);

        userMapper.register(user);
    }

    @Override
    public User findUserIsExist(User user) {
        User userIsExist = userMapper.findUserIsExist(user);
        return userIsExist;
    }

    @Override
    public User login(User user) {
        User userIsExist = userMapper.findUserIsExist(user);
        if (userIsExist == null) {
            //不存在该账号
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if (!Md5Util.getMD5String(user.getPassword()).equals(userIsExist.getPassword())) {
            //密码错误
            throw new AccountNotFoundException(MessageConstant.PASSWORD_ERROR);
        }
        return userIsExist;
    }

    @Override
    public User queryUserById(Integer id) {
        User user = userMapper.queryUserById(id);
        return user;
    }

    @Override
    public void updateUserInfo(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateUserInfo(user);
    }

    @Override
    public void deleteUserInfo(Integer id) {
        userMapper.deleteUserInfo(id);
    }

    @Override
    public PageResult queryUserList(UserPageDTO userPageDTO) {
        //1.获取记录总数
        Long total = userMapper.count();

        //2.获取分页查询的列表
        int page = userPageDTO.getPage();
        int pageSize = userPageDTO.getPageSize();
        String username = userPageDTO.getUsername();
        String email = userPageDTO.getEmail();

        Integer start = (page - 1) * pageSize;
        List<User> records = userMapper.page(start, pageSize, username, email);

        //3.封装pageResult对象
        PageResult pageResult = new PageResult(total, records);
        return pageResult;
    }
}
