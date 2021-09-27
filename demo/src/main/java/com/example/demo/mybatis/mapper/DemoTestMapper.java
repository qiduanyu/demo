package com.example.demo.mybatis.mapper;

import com.example.demo.quartz.*;
import com.example.demo.quartz.Equipment.Equip;
import com.example.demo.quartz.Equipment.EquipMin;
import com.example.demo.quartz.Equipment.EquipmentParam;
import com.example.demo.quartz.Equipment.FacilitiesEquipmentMin;
import com.example.demo.quartz.Mapper.*;
import com.example.demo.quartz.asset.*;
import com.example.demo.quartz.asset.assetValue.AssetValueParam;
import com.example.demo.quartz.asset.assetValue.AssetValueResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DemoTestMapper {

    Captcha queryCaptcha();

    LoginUser queryUserByAccount(@Param("account")String account);

    LoginUser queryUserByToken(@Param("token") String token);

    List<Menu> queryMenusByUserId(@Param("userId")String userId);

    List<Menu> queryMenusByParentId(@Param("parentId")String parentId);

    String queryPhoneByAccount(@Param("account")String account);

    LoginUser queryUserByPhone(@Param("phone")String phone);

    List<Project> queryProjectsByUserId(@Param("userId")String userId);

    List<Project> queryProjectsByParentId(@Param("parentId")String parentId);

    List<FacilitiesMid> queryFacilitiesByProjectIdAndType(@Param("allSearch")AllSearch allSearch, @Param("type")String type);

    List<FacilitiesMid> queryFacilitiesByProjectIdAndTypeRight(@Param("allSearch")AllSearch allSearch, @Param("type")String type,@Param("facilitiesCode") String facilitiesCode);

//    String queryTypeCodeByFacilitiesMidCode(@Param("FacilitiesMidCode")String FacilitiesMidCode);

    List<FacilitiesMin> queryFacilitiesMinByProjectIdAndFacilitiesMid(@Param("allSearch")AllSearchTwo allSearch);

    List<FacilitiesAttr> queryAttrByFacilitiesCode(@Param("facilitiesCode")String facilitiesCode);

    List<FacilitiesAttrDetails> queryDetailsByFacilitiesCodeAndAttr(@Param("facilitiesCode")String facilitiesCode,@Param("attr")String attr);

    void insertTestData(@Param("project") ProjectMapper project);

    void insertTestMenuData(@Param("menu") MenuMapper menu);

    void insertTestFacilitiesData(@Param("facilitiesMidMapper") FacilitiesMidMapper facilitiesMidMapper);

    String selectProjectCodeByF(@Param("string")String facilitiesCode);

    void insertTestAttr(@Param("facilitiesAttrMapper") FacilitiesAttrMapper facilitiesAttrMapper);

    void insertTestAttrDetail(@Param("facilitiesAttrDetailsMapper") FacilitiesAttrDetailsMapper facilitiesAttrDetailsMapper);

    List<FacilitiesClass> selectProjectByCode(@Param("projectCode")String projectCode);

    List<FacilitiesClass> selectProjectByParentCode(@Param("projectCode")String projectCode);

    List<FacilitiesClass> selectFacByproject(@Param("projectCode")String projectCode, @Param("s")String s ,@Param("typeCode") String typeCode);

    String queryEquipAmountByFacilitiesMidCode(@Param("equipmentParam")EquipmentParam equipmentParam);

    String queryNameByProjectCodeAndFacilitiesMidCode(@Param("equipmentParam")EquipmentParam equipmentParam);

    List<FacilitiesEquipmentMin> queryFacilitiesEquipmentMinListByProjectCodeAndFacilitiesMidCode(@Param("equipmentParam") EquipmentParam equipmentParam);

    List<EquipMin> queryequipMinListByProjectCodeAndFacilitiesMidCodeAndFacilitiesMinCode(@Param("equipmentParam") EquipmentParam equipmentParam, @Param("facilitiesMinCode") String facilitiesMinCode);

    List<Equip> queryequipListByProjectCodeAndFacilitiesMidCodeAndFacilitiesMinCodeAndByEquipMinName(@Param("equipmentParam") EquipmentParam equipmentParam, @Param("facilitiesMinCode") String facilitiesMinCode,@Param("equipMinName") String equipMinName);

    FacilitiesEquipmentMin queryFacilitiesEquipmentMinListByProjectCodeAndFacilitiesMinCode(@Param("equipmentParam")EquipmentParam equipmentParam);

    List<FacilitiesAttr> queryAttrByEquipCode(@Param("equipmentParam")EquipmentParam equipmentParam);

    List<FacilitiesAttrDetails> queryDetailsByEquipCodeAndAttr(@Param("equipmentParam")EquipmentParam equipmentParam,@Param("attr")String attr);

    List<FacilitiesNameResult> queryFacilitiesName(@Param("facilitiesNameParam") FacilitiesNameParam facilitiesNameParam);

    void deleteByProjectCodeAndFac(@Param("allSearchAttr") AllSearchAttr allSearchAttr);

    List<AssetAllResult> queryProjectNameByCode(@Param("projectCodes") List<String> projectCodes);

    TotalInvestment queryTotalInvestment(@Param("projectCode") String projectCode);

    SewageTreatment querySewageTreatment(@Param("projectCode") String projectCode);

    PumpStation queryPumpStation(@Param("projectCode")String projectCode);

    StoragePool queryStoragePool(@Param("projectCode")String projectCode);

    DrainageNetwork queryDrainageNetwork(@Param("projectCode")String projectCode);

    TownWaterSupplyPlant queryTownWaterSupplyPlant(@Param("projectCode")String projectCode);

    WaterSupplyPipeNetwork queryWaterSupplyPipeNetwork(@Param("projectCode") String projectCode);

    ArtificialWetland queryArtificialWetland(@Param("projectCode") String projectCode);

    River queryRiver(@Param("projectCode") String projectCode);

    AlongTheRiver queryAlongTheRiver(@Param("projectCode") String projectCode);

    Road queryRoad(@Param("projectCode") String projectCode);

    Park queryPark(@Param("projectCode") String projectCode);

    PublicBuildingsAndResidentialAreas queryPublicBuildingsAndResidentialAreas(@Param("projectCode") String projectCode);

    Lakes queryLakes(@Param("projectCode") String projectCode);

    Reservoir queryReservoir(@Param("projectCode") String projectCode);

    NaturalWetlands queryNaturalWetlands(@Param("projectCode") String projectCode);

    SpongeFacilities querySpongeFacilities(@Param("projectCode") String projectCode);

    List<AssetValueResult> queryAsserValue(@Param("assetValueParam") AssetValueParam assetValueParam);


    Integer insertTestFacilitiesEightData(@Param("facilitiesMin") FacilitiesMinEight facilitiesMin);


    FacilitiesMinEight queryFacilitiesMinByMinCodeAndProjectCode(@Param("facilitiesMin") FacilitiesMinEight facilitiesMinEight);

    Integer testAddProject(@Param("project") Project project);

    Project queryProjectByProjectCode(@Param("projectCode") String projectCode);

    void testAddProjectToUser(@Param("projectCode") String projectCode,@Param("userId") String userId);

}
