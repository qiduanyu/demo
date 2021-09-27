package com.example.demo.quartz;

import lombok.Data;

import java.util.List;

@Data
public class AllSearch {

    private List<String> projectCode;

    //类型为空，查询所有，
    //1 根据设施查设施 2 根据设备查设施
    private String searchType;

    private String searchVal;
}
