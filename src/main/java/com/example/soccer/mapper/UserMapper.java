package com.example.soccer.mapper;

import com.example.soccer.pojo.User;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO tb_user (username, password, email, image, create_time, update_time) values (#{username},#{password},#{email},#{image},#{createTime},#{updateTime})")
    void register(User user);

    @Select("select * from tb_user where username=#{username}")
    User findUserIsExist(User user);

    @Select("select username,email,image,status,type,create_time,update_time from tb_user where id=#{id}")
    User queryUserById(Integer id);

    void updateUserInfo(User user);

    @Delete("delete from tb_user where id=#{id}")
    void deleteUserInfo(Integer id);

    @Select("select count(*) from tb_user")
    Long count();

    List<User> page(Integer start, Integer pageSize, String username, String email);
}
