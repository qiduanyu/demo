package com.example.demo.quartz;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {
    private String menuId;
    private String menuCode;
    private String menuName;
    private String path;
    private String icon;
    private String permission;
    private List<Menu> children = new ArrayList<>();
}
