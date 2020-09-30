package com.example.demo.service;

import com.example.demo.dto.user.User;
import com.example.demo.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void add(User user) {
        userMapper.add(user);
    }
}
