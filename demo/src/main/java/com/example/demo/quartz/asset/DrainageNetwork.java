package com.example.demo.quartz.asset;

import lombok.Data;

@Data
public class DrainageNetwork {
    private String combinedLength;
    private String sewagePipeLength;
    private String stormSewerLength;
    private String confluencePipeLength;
    private String interceptingPipeLength;
    private String interceptingClosureWell;
    private String interceptingInspectionWell;
}
