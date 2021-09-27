package com.example.demo.quartz.Mapper;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuMapper {
    private String menuId;
    private String menuCode;
    private String menuName;
    private String path;
    private String icon;
    private String permission;
    private List<MenuMapper> children = new ArrayList<>();
    private String parentId;
}
