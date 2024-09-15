package com.example.soccer.service;

import com.example.soccer.dto.UserPageDTO;
import com.example.soccer.pojo.PageResult;
import com.example.soccer.pojo.User;

public interface UserService {

    void register(User user);

    User findUserIsExist(User user);

    User login(User user);

    User queryUserById(Integer id);

    void updateUserInfo(User user);

    void deleteUserInfo(Integer id);

    PageResult queryUserList(UserPageDTO userPageDTO);
}
