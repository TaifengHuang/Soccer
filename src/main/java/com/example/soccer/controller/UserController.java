package com.example.soccer.controller;

import com.example.soccer.constant.MessageConstant;
import com.example.soccer.dto.UserPageDTO;
import com.example.soccer.pojo.PageResult;
import com.example.soccer.pojo.Result;
import com.example.soccer.pojo.User;
import com.example.soccer.service.UserService;
import com.example.soccer.utils.JwtUtil;
import com.example.soccer.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        userService.register(user);
        return Result.success(MessageConstant.REGISTER_SUCCESS);
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User loginUser = userService.login(user);
        //登录成功
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        claims.put("username", loginUser.getUsername());
        //生成token
        String token = JwtUtil.genToken(claims);
        //把token存储到redis中
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(token,token,1, TimeUnit.HOURS);
        //返回token
        return Result.success(token);
    }

    /**
     * 通过id查询用户信息
     * @param id
     * @return
     */
    @PostMapping("/userInfo")
    public Result<User> queryUserById(@RequestParam Integer id){
        User user = userService.queryUserById(id);
        return Result.success(user);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PostMapping("/updateUserInfo")
    public Result<String> updateUserInfo(@RequestBody User user){
        userService.updateUserInfo(user);
        return Result.success(MessageConstant.UPDATE_SUCCESS);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @PostMapping("/deleteUserInfo")
    public Result<String> deleteUserInfo(@RequestParam Integer id){
        userService.deleteUserInfo(id);
        return Result.success(MessageConstant.DELETE_SUCCESS);
    }

    /**
     * 分页查询用户列表
     * @param userPageDTO
     * @return
     */
    @PostMapping("/queryUserList")
    public Result<PageResult> queryUserList(@RequestBody UserPageDTO userPageDTO){
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id=(Integer)map.get("id");
        PageResult pageResult = userService.queryUserList(userPageDTO);
        return Result.success(pageResult);
    }
}
