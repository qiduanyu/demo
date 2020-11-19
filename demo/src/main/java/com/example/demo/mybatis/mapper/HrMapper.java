package com.example.demo.mybatis.mapper;

import com.example.demo.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface HrMapper {
    /**
     * 通过用户名查询用户信息
     * @param s 用户名
     * @return 用户信息
     */
    User loadUserByUsername(@Param("username") String s);
}
