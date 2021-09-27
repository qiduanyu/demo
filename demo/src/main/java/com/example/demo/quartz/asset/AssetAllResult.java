package com.example.demo.quartz.asset;

import lombok.Data;

import java.util.List;

@Data
public class AssetAllResult {

    private String projectCode;
    private String projectName;

    private AssetStatistics assetStatistics;
}
