package com.example.demo.quartz;

import lombok.Data;

import java.util.List;

@Data
public class LoginUser {
    private String userId;
    private String username;
    private List<Menu> menus;
    private String token;
}
