package com.example.demo.quartz.Mapper;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectMapper {
    // 项目编码
    private String projectCode;
    // 项目名称
    private String projectName;
    // 子项目
    private List<ProjectMapper> children = new ArrayList<>();
    // 父项目id
    private String parentId;
}
