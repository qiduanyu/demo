package com.example.demo.quartz.Equipment;

import lombok.Data;

import java.util.List;

@Data
public class FacilitiesEquipmentMin {

    private String facilitiesMinCode;

    private String equipAmount;

    private List<EquipMin> equipMinList;

}
