package com.example.demo.quartz.asset;

import lombok.Data;

@Data
public class AssetStatistics {
    private String totalInvestment;
    private SewageTreatment sewageTreatment;
    private PumpStation pumpStation;
    private StoragePool storagePool;
    private DrainageNetwork drainageNetwork;
    private TownWaterSupplyPlant townWaterSupplyPlant;
    private WaterSupplyPipeNetwork waterSupplyPipeNetwork;
    private ArtificialWetland artificialWetland;
    private River river;
    private AlongTheRiver alongTheRiver;
    private Road road;
    private Park park;
    private PublicBuildingsAndResidentialAreas publicBuildingsAndResidentialAreas;
    private Lakes lakes;
    private Reservoir reservoir;
    private NaturalWetlands naturalWetlands;
    private SpongeFacilities spongeFacilities;


}
