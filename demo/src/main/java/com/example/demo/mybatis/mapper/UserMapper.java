package com.example.demo.mybatis.mapper;

import com.example.demo.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    public void add(@Param("user") User user);

}
