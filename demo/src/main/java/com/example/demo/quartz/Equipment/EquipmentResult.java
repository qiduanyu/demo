package com.example.demo.quartz.Equipment;

import lombok.Data;

import java.util.List;

@Data
public class EquipmentResult {

    private String facilitiesMidName;

    private String equipAmount;

    private List<FacilitiesEquipmentMin> facilitiesMinList;

}
