package com.example.soccer.service;

import com.example.soccer.dto.UserPageDTO;
import com.example.soccer.pojo.PageResult;
import com.example.soccer.pojo.User;

import java.util.Map;

public interface UserService {

    void register(User user);

    User findUserIsExist(User user);

    User login(User user);

    User queryUserById(Integer id);

    void updateUserInfo(User user);

    void deleteUserInfo(Integer id);

    PageResult queryUserList(UserPageDTO userPageDTO);

    void updateUserPassword(Map<String, Object> map, Integer id);

    void updateUserImage(String imageUrl, Integer id);
}
