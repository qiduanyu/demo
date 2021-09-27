package com.example.demo.quartz.Mapper;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FacilitiesAttrMapper {

    private String attributeId;

    private String value;

    private String facilitiesCode;

    private List<Map<String,Object>> facilitiesAttrDetails;

}
