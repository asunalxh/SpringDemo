package com.example.springdemo.mapper;

import com.example.springdemo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @created: 2021/05/24 18:00
 * @description:
 */

@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> getAllUser();

    @Select("select * from user where id = #{id}")
    User getUserByID(String id);

    @Insert("insert into user(id,name,password,type,info,remark) values(#{id},#{name},#{password},#{type},#{info},#{remark})")
    void insertUser(User user);

    @Delete("delete from user where id=#{id}")
    void delUser(String id);

    @Update("update user set name=#{name}, type=#{type}, info=#{info}, remark=#{remark}, password=#{password} where id=#{id}")
    void updateUser(User user);
}
