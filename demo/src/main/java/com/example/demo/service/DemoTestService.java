package com.example.demo.service;

import com.example.demo.mybatis.mapper.DemoTestMapper;
import com.example.demo.quartz.*;
import com.example.demo.quartz.Equipment.*;
import com.example.demo.quartz.Mapper.*;
import com.example.demo.quartz.asset.*;
import com.example.demo.quartz.asset.assetValue.AssetValueParam;
import com.example.demo.quartz.asset.assetValue.AssetValueResult;
import com.example.demo.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Slf4j
public class DemoTestService {

    @Autowired
    private DemoTestMapper demoTestMapper;

    @Autowired
    private AsyncService asyncService;

    private final static String[] PERMISSION =new String[]{"select","insert","update"};

    private final static String[] TYPE =new String[]{"WS","GS","NW","PG","GG","BZ","TX","YT","SG","GY","GZ","DL","HD","HP","SK","RS","ZS"};

    private final static Map<String,String> typeMap = new HashMap<>();

    private final static Map<Integer,String> attrMap = new HashMap<>();

    private final static Map<String,String> FacilitiesType = new HashMap<>();

    private final static Map<String,Map<String,String>> minTypeMap = new HashMap<>();
    private final static Map<String,String> WSTypeMap = new HashMap<>();
//    private final static Map<String,String> GSTypeMap = new HashMap<>();
//    private final static Map<String,String> NWTypeMap = new HashMap<>();
//    private final static Map<String,String> PGTypeMap = new HashMap<>();
//    private final static Map<String,String> GGTypeMap = new HashMap<>();
//    private final static Map<String,String> BZTypeMap = new HashMap<>();
//    private final static Map<String,String> TXTypeMap = new HashMap<>();
//    private final static Map<String,String> YTTypeMap = new HashMap<>();
//    private final static Map<String,String> SGTypeMap = new HashMap<>();
//    private final static Map<String,String> GYTypeMap = new HashMap<>();
//    private final static Map<String,String> GZTypeMap = new HashMap<>();
//    private final static Map<String,String> DLTypeMap = new HashMap<>();
//    private final static Map<String,String> HDTypeMap = new HashMap<>();
//    private final static Map<String,String> HPTypeMap = new HashMap<>();
//    private final static Map<String,String> SKTypeMap = new HashMap<>();
//    private final static Map<String,String> RSTypeMap = new HashMap<>();
//    private final static Map<String,String> ZSTypeMap = new HashMap<>();

    static {
        typeMap.put(TYPE[0],"城镇污水处理厂");
        typeMap.put(TYPE[1],"城镇供水厂");
        typeMap.put(TYPE[2],"农村污水处理设施");
        typeMap.put(TYPE[3],"排水管网及附属设施");
        typeMap.put(TYPE[4],"给水管网及附属设施");
        typeMap.put(TYPE[5],"泵站");
        typeMap.put(TYPE[6],"调蓄池");
        typeMap.put(TYPE[7],"一体化设施");
        typeMap.put(TYPE[8],"水工建筑物");
        typeMap.put(TYPE[9],"公园");
        typeMap.put(TYPE[10],"公共建筑及住宅小区");
        typeMap.put(TYPE[11],"道路");
        typeMap.put(TYPE[12],"河道");
        typeMap.put(TYPE[13],"湖泊");
        typeMap.put(TYPE[14],"水库");
        typeMap.put(TYPE[15],"人工湿地");
        typeMap.put(TYPE[16],"自然湿地");

        WSTypeMap.put("01","污水泵房");
        WSTypeMap.put("02","格栅间");
        WSTypeMap.put("03","计量槽（井）");
        WSTypeMap.put("04","配水井");
        WSTypeMap.put("05","沉砂池");
        WSTypeMap.put("06","洗砂间");
        WSTypeMap.put("07","调节池");
        WSTypeMap.put("08","事故池");
        WSTypeMap.put("09","初次沉淀池");
        WSTypeMap.put("10","水解酸化池");
        WSTypeMap.put("11","气浮池");
        WSTypeMap.put("12","二级生物处理池");
        WSTypeMap.put("13","除磷池");
        WSTypeMap.put("14","二次沉淀池");
        WSTypeMap.put("15","高密度沉淀池");
        WSTypeMap.put("16","滤池");
        WSTypeMap.put("17","接触池");
        WSTypeMap.put("18","综合加药间");
        WSTypeMap.put("19","清水池");
        WSTypeMap.put("20","污泥泵房");
        WSTypeMap.put("21","污泥浓缩池");
        WSTypeMap.put("22","储泥池");
        WSTypeMap.put("23","污泥脱水机房");
        WSTypeMap.put("24","污泥干燥间");
        WSTypeMap.put("25","污泥堆置棚");
        WSTypeMap.put("26","鼓风机房");
        WSTypeMap.put("27","高压配电室");
        WSTypeMap.put("28","低压配电室");
        WSTypeMap.put("29","中控、调度室");
        WSTypeMap.put("30","监测室");
        WSTypeMap.put("31","综合用房");
        WSTypeMap.put("98","其他建（构）筑物");
        WSTypeMap.put("99","其他设施");

        minTypeMap.put("WS",WSTypeMap);


        attrMap.put(1,"建安信息");
        attrMap.put(2,"空间信息");
        attrMap.put(3,"属性信息");
        attrMap.put(4,"状态信息");
        attrMap.put(5,"维护信息");
        attrMap.put(6,"档案资料");

        FacilitiesType.put("urbanSewageTreatmentPlantNum","");
    }

    public Captcha captcha() {
        return demoTestMapper.queryCaptcha();
    }

    public LoginUser userLogin(UserLoginParam userLoginParam, HttpServletRequest request) {

        // 根据账号查询人员数据
        LoginUser loginUser = demoTestMapper.queryUserByAccount(userLoginParam.getUsername());
        List<Menu> menuList = demoTestMapper.queryMenusByUserId(loginUser.getUserId());
        for (Menu menu : menuList) {
            List<Menu> menus = queryByParentId(menu.getMenuId());
            menu.setChildren(menus);
        }
        loginUser.setMenus(menuList);
        return loginUser;
    }



    public Verification verification(String phone) {
        String adminPhone = demoTestMapper.queryPhoneByAccount("admin");

        if (!adminPhone.equals(phone)){
            Verification verification = new Verification();
            verification.setSuccess("0");
            verification.setMessage("用户不存在");
            return verification;
        }
        Verification verification = new Verification();
        verification.setSuccess("1");
        verification.setMessage("验证码发送成功");
        verification.setExpectTime(300);
        return verification;
    }

    public LoginUser smsLogin(SmsLoginParam smsLoginParam) {
        String adminPhone = demoTestMapper.queryPhoneByAccount("admin");
        if (!adminPhone.equals(smsLoginParam.getPhone())){
            Verification verification = new Verification();
            verification.setSuccess("0");
            verification.setMessage("用户不存在");
            return null;
        }
        LoginUser loginUser = demoTestMapper.queryUserByPhone(smsLoginParam.getPhone());
        List<Menu> menuList = demoTestMapper.queryMenusByUserId(loginUser.getUserId());
        for (Menu menu : menuList) {
            List<Menu> menus = queryByParentId(menu.getMenuId());
            menu.setChildren(menus);
        }
        loginUser.setMenus(menuList);
        return loginUser;
    }

    public Object currentUserProject(HttpServletRequest request) {
        // 根据账号查询人员数据
        String token = request.getHeader("Authorization");
        LoginUser loginUser = demoTestMapper.queryUserByToken(token);
        List<Project> projectList = demoTestMapper.queryProjectsByUserId(loginUser.getUserId());
        for (Project project : projectList) {
            List<Project> projects = queryProjectByParentId(project.getProjectCode());
            project.setChildren(projects);
        }
        return projectList;
    }



    private List<Menu> queryByParentId(String parentId){
        List<Menu> menuList = demoTestMapper.queryMenusByParentId(parentId);
        if (!CollectionUtils.isEmpty(menuList)){
            for (Menu menu : menuList) {
                List<Menu> menus = queryByParentId(menu.getMenuId());
                menu.setChildren(menus);
            }
        }
        return menuList;
    }

    private List<Project> queryProjectByParentId(String parentId){
        List<Project> projectList = demoTestMapper.queryProjectsByParentId(parentId);
        if (!CollectionUtils.isEmpty(projectList)){
            for (Project project : projectList) {
                List<Project> projects = queryProjectByParentId(project.getProjectCode());
                project.setChildren(projects);
            }
        }
        return projectList;
    }


    public Object all(AllSearch allSearch) {

        Map<String,List<Object>> resultMap = new HashMap<>();
        allSearch.setSearchType("2");
        //根据设施查设施
        if ("1".equals(allSearch.getSearchType())){
            List<FacilitiesMid> facilitiesMids = demoTestMapper.queryFacilitiesByProjectIdAndType(allSearch,"mid");
            resultMap.put("facilitiesMids",new ArrayList<>(facilitiesMids));
            List<FacilitiesMid> facilitiesMidList = demoTestMapper.queryFacilitiesByProjectIdAndType(allSearch,"min");
            List<FacilitiesMin> facilitiesMins = new ArrayList<>();
            for (FacilitiesMid facilitiesMid : facilitiesMidList) {
                FacilitiesMin facilitiesMin = new FacilitiesMin();
                if (facilitiesMid.getFacilitiesCode().length() > 4){
                    facilitiesMin.setMidTypeCode(facilitiesMid.getFacilitiesCode().substring(1,3));
                }
                BeanUtils.copyProperties(facilitiesMid,facilitiesMin);
                facilitiesMins.add(facilitiesMin);
            }
            resultMap.put("facilitiesMins",new ArrayList<>(facilitiesMins));
        }else if ("2".equals(allSearch.getSearchType())){
            //根据设备查设施
            List<FacilitiesMid> facilitiesMids = demoTestMapper.queryFacilitiesByProjectIdAndTypeRight(allSearch,"mid",null);
            resultMap.put("facilitiesMids",new ArrayList<>(facilitiesMids));

            // 创建返回对象
            List<FacilitiesMin> facilitiesMins = new ArrayList<>();

            // 创建计数器
            int max = facilitiesMids.size();
            final CountDownLatch latch = new CountDownLatch(max);

            // 存储线程list对象
            List<Future<List<FacilitiesMin>>> tempList = new ArrayList<>();

            for (FacilitiesMid facilitiesMid : facilitiesMids) {
                Future<List<FacilitiesMin>> listFuture = asyncService.asyncFacilitieTransferMinByGroup(allSearch, facilitiesMid.getFacilitiesCode());

                tempList.add(listFuture);

                latch.countDown();
            }

            //  等待所有异步处理完毕
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 8 取数据
            for (Future<List<FacilitiesMin>> listFuture : tempList) {
                try {
                    if (null != listFuture){
                        List<FacilitiesMin> list = listFuture.get();
                        log.info(list.size()+"");
                        facilitiesMins.addAll(list);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            resultMap.put("facilitiesMins",new ArrayList<>(facilitiesMins));
        } else {
            //查询所有
            List<FacilitiesMid> facilitiesMids = demoTestMapper.queryFacilitiesByProjectIdAndType(allSearch,"mid");
            resultMap.put("facilitiesMids",new ArrayList<>(facilitiesMids));
            List<FacilitiesMid> facilitiesMidList = demoTestMapper.queryFacilitiesByProjectIdAndType(allSearch,"min");
            List<FacilitiesMin> facilitiesMins = new ArrayList<>();
            for (FacilitiesMid facilitiesMid : facilitiesMidList) {
                FacilitiesMin facilitiesMin = new FacilitiesMin();
                if (facilitiesMid.getFacilitiesCode().length() > 4){
                    facilitiesMin.setMidTypeCode(facilitiesMid.getFacilitiesCode().substring(1,3));
                }
                BeanUtils.copyProperties(facilitiesMid,facilitiesMin);
                facilitiesMins.add(facilitiesMin);
            }
            resultMap.put("facilitiesMins",new ArrayList<>(facilitiesMins));
        }


        return resultMap;
    }

    public Object midToMin(AllSearchTwo allSearch) {
        AllSearch allSearch1 = new AllSearch();
        List<String> projectCodes = new ArrayList<>();
        projectCodes.add(allSearch.getProjectCode());
        allSearch1.setProjectCode(projectCodes);
        List<FacilitiesMid> facilitiesMids = demoTestMapper.queryFacilitiesByProjectIdAndType(allSearch1,"mid");
        String typeCode = facilitiesMids.get(0).getTypeCode();

        List<FacilitiesMin> facilitiesMins = demoTestMapper.queryFacilitiesMinByProjectIdAndFacilitiesMid(allSearch);
        for (FacilitiesMin facilitiesMin : facilitiesMins) {
            facilitiesMin.setMidTypeCode(typeCode);
            facilitiesMin.setProjectCode(allSearch.getProjectCode());
        }

        return facilitiesMins;
    }

    public Object attribute(AllSearchAttr allSearchAttr) {
        List<FacilitiesAttr> facilitiesAttrs = demoTestMapper.queryAttrByFacilitiesCode(allSearchAttr.getFacilitiesCode());
        for (FacilitiesAttr facilitiesAttr : facilitiesAttrs) {
            List<FacilitiesAttrDetails> facilitiesAttrDetails = demoTestMapper.queryDetailsByFacilitiesCodeAndAttr(allSearchAttr.getFacilitiesCode(), facilitiesAttr.getAttributeId());
            List<Map<String,Object>> mapList = new ArrayList<>();
            for (FacilitiesAttrDetails facilitiesAttrDetail : facilitiesAttrDetails) {
                Map<String,Object> map = new HashMap<>();
                map.put("key",facilitiesAttrDetail.getKey());
                map.put("keyDesc",facilitiesAttrDetail.getKeyDesc());
                if ("2".equals(facilitiesAttrDetail.getDataType())){
                    String[] split = facilitiesAttrDetail.getDataValStr().split(",");
                    map.put("dataVal",new ArrayList<>(Arrays.asList(split)));
                } else {
                    map.put("dataVal",facilitiesAttrDetail.getDataValStr());
                }
                map.put("dataValType",facilitiesAttrDetail.getDataValType());
                map.put("dataType",facilitiesAttrDetail.getDataType());
                mapList.add(map);
            }
            facilitiesAttr.setDetails(mapList);
        }
        return facilitiesAttrs;
    }

    public Object projectAll(String projectCode) {
        List<FacilitiesClass> projectList = demoTestMapper.selectProjectByCode(projectCode);
        List<FacilitiesClass> childrenList = demoTestMapper.selectProjectByParentCode(projectCode);
        if (!CollectionUtils.isEmpty(childrenList)){
            int num = 0;
            for (FacilitiesClass facilitiesClass : childrenList) {
                num += facilitiesClass.getTotalnum();
                facilitiesClass.setLevel("2");
            }
            FacilitiesClass childrenP = new FacilitiesClass();
            childrenP.setLevel("2");
            childrenP.setTotalnum(num);
            childrenP.setType("CProject");
            childrenP.setName(projectList.get(0).getName()+"子项目");
            List<FacilitiesClass> resChildrenList = new ArrayList<>();
            resChildrenList.add(childrenP);
            projectList.get(0).setChildren(resChildrenList);
        }
        List<FacilitiesClass> facMidList = demoTestMapper.selectFacByproject(projectCode,"1","");
        for (FacilitiesClass facilitiesClass : facMidList) {
            List<FacilitiesClass> facMinList = demoTestMapper.selectFacByproject(projectCode,"2",facilitiesClass.getType());
            for (FacilitiesClass aClass : facMinList) {
                aClass.setLevel("4");
            }
            facilitiesClass.setLevel("3");
            facilitiesClass.setChildren(facMinList);
        }
        if (CollectionUtils.isEmpty(childrenList)){
            projectList.get(0).setChildren(facMidList);
        } else {
            projectList.get(0).getChildren().get(0).setChildren(facMidList);
        }
        projectList.get(0).setLevel("1");
        return projectList.get(0);
    }


//    public Object projectAll(String projectCode) {
//        List<ProjectAll> projectAllList = demoTestMapper.selectProjectByCode(projectCode);
//        List<ProjectAll> childrenList = demoTestMapper.selectProjectByParentCode(projectCode);
//        List<FacilitiesMidTypes> facMinList = demoTestMapper.selectFacByproject(projectCode,"2");
//        for (ProjectAll projectAll : childrenList) {
//            List<FacilitiesMidTypes> facMidList = demoTestMapper.selectFacByproject(projectAll.getProjectCode(),"1");
//            projectAll.setFacilitiesMidTypes(facMidList);
//        }
//
//
//
//
//        projectAllList.get(0).setProjectAll(childrenList);
//
//        return projectAllList;
//    }
    public Object equipmentList(EquipmentParam equipmentParam) {
        EquipmentResult equipmentResult = new EquipmentResult();
        String name = demoTestMapper.queryNameByProjectCodeAndFacilitiesMidCode(equipmentParam);
        String amount = demoTestMapper.queryEquipAmountByFacilitiesMidCode(equipmentParam);
        equipmentResult.setEquipAmount(amount);
        equipmentResult.setFacilitiesMidName(name);
        List<FacilitiesEquipmentMin> facilitiesEquipmentMinList;
        facilitiesEquipmentMinList = demoTestMapper.queryFacilitiesEquipmentMinListByProjectCodeAndFacilitiesMidCode(equipmentParam);
        equipmentResult.setFacilitiesMinList(facilitiesEquipmentMinList);

        for (FacilitiesEquipmentMin facilitiesEquipmentMin : facilitiesEquipmentMinList) {
            List<EquipMin> equipMinList;
            equipMinList = demoTestMapper.queryequipMinListByProjectCodeAndFacilitiesMidCodeAndFacilitiesMinCode(equipmentParam,facilitiesEquipmentMin.getFacilitiesMinCode());
            facilitiesEquipmentMin.setEquipMinList(equipMinList);
            for (EquipMin equipMin : equipMinList) {
                List<Equip> equipList;
                equipList = demoTestMapper.queryequipListByProjectCodeAndFacilitiesMidCodeAndFacilitiesMinCodeAndByEquipMinName(equipmentParam,facilitiesEquipmentMin.getFacilitiesMinCode(),equipMin.getEquipMinName());
                equipMin.setEquipList(equipList);
            }
        }
        return equipmentResult;
    }


    public Object equipmentMinList(EquipmentParam equipmentParam) {

        FacilitiesEquipmentMin facilitiesEquipmentMin = demoTestMapper.queryFacilitiesEquipmentMinListByProjectCodeAndFacilitiesMinCode(equipmentParam);
        List<EquipMin> equipMinList = demoTestMapper.queryequipMinListByProjectCodeAndFacilitiesMidCodeAndFacilitiesMinCode(equipmentParam,facilitiesEquipmentMin.getFacilitiesMinCode());
        for (EquipMin equipMin : equipMinList) {
            List<Equip> equipList;
            equipList = demoTestMapper.queryequipListByProjectCodeAndFacilitiesMidCodeAndFacilitiesMinCodeAndByEquipMinName(equipmentParam,facilitiesEquipmentMin.getFacilitiesMinCode(),equipMin.getEquipMinName());
            equipMin.setEquipList(equipList);
        }
        facilitiesEquipmentMin.setEquipMinList(equipMinList);
        return facilitiesEquipmentMin;
    }

    public Object equipmentAttribute(EquipmentParam equipmentParam) {
        List<FacilitiesAttr> attrs = demoTestMapper.queryAttrByEquipCode(equipmentParam);
        for (FacilitiesAttr attr : attrs) {
            List<FacilitiesAttrDetails> facilitiesAttrDetails = demoTestMapper.queryDetailsByEquipCodeAndAttr(equipmentParam,attr.getAttributeId());
            List<Map<String,Object>> mapList = new ArrayList<>();
            for (FacilitiesAttrDetails facilitiesAttrDetail : facilitiesAttrDetails) {
                Map<String,Object> map = new HashMap<>();
                map.put("key",facilitiesAttrDetail.getKey());
                map.put("keyDesc",facilitiesAttrDetail.getKeyDesc());
                if ("2".equals(facilitiesAttrDetail.getDataType())){
                    String[] split = facilitiesAttrDetail.getDataValStr().split(",");
                    map.put("dataVal",new ArrayList<>(Arrays.asList(split)));
                } else {
                    map.put("dataVal",facilitiesAttrDetail.getDataValStr());
                }
                map.put("dataValType",facilitiesAttrDetail.getDataValType());
                map.put("dataType",facilitiesAttrDetail.getDataType());
                mapList.add(map);
            }
            attr.setDetails(mapList);
        }
        return attrs;
    }

    public Object facilitiesName(FacilitiesNameParam facilitiesNameParam) {
//        List<FacilitiesNameResult> facilitiesNameResults = demoTestMapper.queryFacilitiesName(facilitiesNameParam);
        return demoTestMapper.queryFacilitiesName(facilitiesNameParam);
    }

    @Transactional
    public Object facilitiesDelete(AllSearchAttr allSearchAttr) {
        demoTestMapper.deleteByProjectCodeAndFac(allSearchAttr);
        return null;
    }

    public Object assetAll(AssetAll assetAll) {
        List<AssetAllResult> assetAllResult;
        assetAllResult = demoTestMapper.queryProjectNameByCode(assetAll.getProjectCodes());
        for (AssetAllResult allResulta : assetAllResult) {
            String projectCode = allResulta.getProjectCode();
            AssetStatistics allResult = new AssetStatistics();
            TotalInvestment totalInvestment = demoTestMapper.queryTotalInvestment(projectCode);
            allResult.setTotalInvestment(totalInvestment != null ? totalInvestment.getTotalInvestment() : "");
            SewageTreatment sewageTreatment = demoTestMapper.querySewageTreatment(projectCode);
            allResult.setSewageTreatment(sewageTreatment != null ? sewageTreatment : new SewageTreatment());
            PumpStation pumpStation = demoTestMapper.queryPumpStation(projectCode);
            allResult.setPumpStation(pumpStation != null ? pumpStation : new PumpStation());
            StoragePool storagePool = demoTestMapper.queryStoragePool(projectCode);
            allResult.setStoragePool(storagePool != null ? storagePool : new StoragePool());
            DrainageNetwork drainageNetwork = demoTestMapper.queryDrainageNetwork(projectCode);
            allResult.setDrainageNetwork(drainageNetwork != null ? drainageNetwork : new DrainageNetwork());
            TownWaterSupplyPlant townWaterSupplyPlant = demoTestMapper.queryTownWaterSupplyPlant(projectCode);
            allResult.setTownWaterSupplyPlant(townWaterSupplyPlant != null ? townWaterSupplyPlant : new TownWaterSupplyPlant());
            WaterSupplyPipeNetwork waterSupplyPipeNetwork = demoTestMapper.queryWaterSupplyPipeNetwork(projectCode);
            allResult.setWaterSupplyPipeNetwork(waterSupplyPipeNetwork != null ? waterSupplyPipeNetwork : new WaterSupplyPipeNetwork());
            ArtificialWetland artificialWetland = demoTestMapper.queryArtificialWetland(projectCode);
            allResult.setArtificialWetland(artificialWetland != null ? artificialWetland : new ArtificialWetland());
            River river = demoTestMapper.queryRiver(projectCode);
            allResult.setRiver(river != null ? river : new River());
            AlongTheRiver alongTheRiver = demoTestMapper.queryAlongTheRiver(projectCode);
            allResult.setAlongTheRiver(alongTheRiver != null ? alongTheRiver : new AlongTheRiver());
            Road road = demoTestMapper.queryRoad(projectCode);
            allResult.setRoad(road != null ? road : new Road());
            Park park = demoTestMapper.queryPark(projectCode);
            allResult.setPark(park != null ? park : new Park());
            PublicBuildingsAndResidentialAreas publicBuildingsAndResidentialAreas = demoTestMapper.queryPublicBuildingsAndResidentialAreas(projectCode);
            allResult.setPublicBuildingsAndResidentialAreas(publicBuildingsAndResidentialAreas != null ? publicBuildingsAndResidentialAreas : new PublicBuildingsAndResidentialAreas());
            Lakes lakes = demoTestMapper.queryLakes(projectCode);
            allResult.setLakes(lakes != null ? lakes : new Lakes());
            Reservoir reservoir = demoTestMapper.queryReservoir(projectCode);
            allResult.setReservoir(reservoir != null ? reservoir : new Reservoir());
            NaturalWetlands naturalWetlands = demoTestMapper.queryNaturalWetlands(projectCode);
            allResult.setNaturalWetlands(naturalWetlands != null ? naturalWetlands : new NaturalWetlands());
            SpongeFacilities spongeFacilities = demoTestMapper.querySpongeFacilities(projectCode);
            allResult.setSpongeFacilities(spongeFacilities != null ? spongeFacilities : new SpongeFacilities());

            allResulta.setAssetStatistics(allResult);
        }

        return assetAllResult;
    }

    public Object assetValue(AssetValueParam assetValueParam) {
        //
        List<AssetValueResult> assetValueResults = demoTestMapper.queryAsserValue(assetValueParam);

        return assetValueResults;
    }

    //增加测试项目
//    public Object testData() {
//        String projectCode = "20180246NXHZZZQ000";
//
//        for (int i = 3; i < 1003; i++) {
//            Project project = new Project();
//            project.setProjectCode(projectCode+i);
//            project.setProjectName("测试项目"+i);
//            project.setParentId("0");
//            demoTestMapper.insertTestData(project);
//        }
//        return null;
//    }
    //增加测试项目子项目数据
    public Object testData() {
        String projectCode = "20180246NXHZZZQ000";

        for (int i = 3; i < 1003; i++) {
            int  number = (int)(Math.random()*100)+1;
            ProjectMapper project = new ProjectMapper();
            project.setProjectCode(projectCode+number+i);
            project.setProjectName("测试项目子项目"+i);
            project.setParentId(projectCode+number);
            demoTestMapper.insertTestData(project);
        }
        return null;
    }

//    //增加测试菜单数据
//    public Object testMenuData() {
//        String menuId = "00";
//
//        for (int i = 4; i < 14; i++) {
//            int  number = (int)(Math.random()*100)+1;
//            Random rand = new Random();
//            int num = rand.nextInt(3);
//            MenuMapper menuMapper = new MenuMapper();
//            menuMapper.setMenuId(menuId+i);
//            menuMapper.setMenuName("测试菜单"+i);
//            menuMapper.setMenuCode("test"+i);
//            menuMapper.setPath("/scshj/test"+i);
//            menuMapper.setPermission(PERMISSION[num]);
//            menuMapper.setParentId("0");
//            demoTestMapper.insertTestMenuData(menuMapper);
//        }
//        return null;
//    }

    //增加测试菜单子菜单数据
    public Object testMenuData() {
        String menuId = "00";
        for (int i = 1; i < 61; i++) {
            int  number = (int)(Math.random()*10);
            Random rand = new Random();
            int num = rand.nextInt(3);
            MenuMapper menuMapper = new MenuMapper();
            menuMapper.setMenuId(menuId+number+i);
            menuMapper.setMenuName("测试菜单"+i);
            menuMapper.setMenuCode("test"+number+i);
            menuMapper.setPath("/scshj/test"+i);
            menuMapper.setPermission(PERMISSION[num]);
            menuMapper.setParentId(menuId+number);
            demoTestMapper.insertTestMenuData(menuMapper);
        }
        return null;
    }

    //增加测试设施数据
//    public Object testFacilitiesData() {
//        String facilitiesCode = "LGY";
//        String projectCode = "20180246NXHZZZQ000";
//        for (int i = 4; i < 100; i++) {
//            Random rand = new Random();
//            int num = rand.nextInt(17);
//            int code = rand.nextInt(100)+1;
//            FacilitiesMidMapper facilitiesMidMapper = new FacilitiesMidMapper();
//            facilitiesMidMapper.setFacilitiesCode(facilitiesCode+new DecimalFormat("0000").format(i));
//            facilitiesMidMapper.setName("测试项目设施");
//            facilitiesMidMapper.setTypeCode(TYPE[num]);
//            facilitiesMidMapper.setTypeVal(typeMap.get(TYPE[num]));
//            facilitiesMidMapper.setProjectCode(projectCode+i);
//            facilitiesMidMapper.setParentFacilitiesCode("0");
//            demoTestMapper.insertTestFacilitiesData(facilitiesMidMapper);
//        }
//        return null;
//    }

    //增加测试设施子设施数据
    @Transactional
    public Object testFacilitiesData() {
        String facilitiesCode1 = "20180246NXHZZZQ000";
        String facilitiesCode2 = "LGY";

        for (int i = 4; i < 10000; i++) {
            Random rand = new Random();
            int num = rand.nextInt(33)+1;
            int num1z99 = rand.nextInt(99)+1;
//            String format = new DecimalFormat("0000").format(i);
            FacilitiesMidMapper facilitiesMidMapper = new FacilitiesMidMapper();
            facilitiesMidMapper.setFacilitiesCode(facilitiesCode2+new DecimalFormat("0000").format(num1z99)+"08"+new DecimalFormat("00000").format(i));
            facilitiesMidMapper.setName("测试项目小类设施");
            String str = new DecimalFormat("00").format(num);
            facilitiesMidMapper.setTypeCode(str);
            facilitiesMidMapper.setTypeVal(WSTypeMap.get(str));
//            String s = demoTestMapper.selectProjectCodeByF();

            facilitiesMidMapper.setProjectCode(facilitiesCode1 + num1z99);
            facilitiesMidMapper.setParentFacilitiesCode(facilitiesCode2+new DecimalFormat("0000").format(num1z99));
            demoTestMapper.insertTestFacilitiesData(facilitiesMidMapper);
        }
        return null;
    }

    //增加测试属性信息
    @Transactional
    public Object testFacilitiesAttrData() {
        String facilitiesCode = "LGY";
        for (int i = 3; i < 1000; i++) {
            for (int j = 1; j < 7; j++) {
                FacilitiesAttrMapper facilitiesAttrMapper = new FacilitiesAttrMapper();
                facilitiesAttrMapper.setFacilitiesCode(facilitiesCode+new DecimalFormat("0000").format(i));
                facilitiesAttrMapper.setValue(attrMap.get(j));
                facilitiesAttrMapper.setAttributeId(String.valueOf(j));
                demoTestMapper.insertTestAttr(facilitiesAttrMapper);
            }
        }
        return null;
    }

    //增加测试属性详细信息
    @Transactional
    public Object testFacilitiesAttrDetailData() {
        String facilitiesCode = "LGY";
        for (int i = 3; i < 700; i++) {
            FacilitiesAttrDetailsMapper facilitiesAttrDetailsMapper = new FacilitiesAttrDetailsMapper();
            facilitiesAttrDetailsMapper.setKey("location");
            facilitiesAttrDetailsMapper.setFacilitiesCode(facilitiesCode+new DecimalFormat("0000").format(i));
            facilitiesAttrDetailsMapper.setDataType("1");
            facilitiesAttrDetailsMapper.setDataValStr("四中桥到南河滩桥二标段（A 区右岸）");
            facilitiesAttrDetailsMapper.setAttributeId("2");
            facilitiesAttrDetailsMapper.setDataValType("1");
            facilitiesAttrDetailsMapper.setKeyDesc("位置描述");
            demoTestMapper.insertTestAttrDetail(facilitiesAttrDetailsMapper);

            facilitiesAttrDetailsMapper.setKey("area");
            facilitiesAttrDetailsMapper.setDataValStr("9.4");
            facilitiesAttrDetailsMapper.setAttributeId("3");
            facilitiesAttrDetailsMapper.setKeyDesc("面积");
            demoTestMapper.insertTestAttrDetail(facilitiesAttrDetailsMapper);

            facilitiesAttrDetailsMapper.setKey("arborVariety");
            facilitiesAttrDetailsMapper.setDataType("2");
            facilitiesAttrDetailsMapper.setDataValStr("国槐,香花槐,云杉,垂柳,金枝槐,新疆杨,樟子松,金枝槐,紫叶李");
            facilitiesAttrDetailsMapper.setKeyDesc("乔木品种");
            demoTestMapper.insertTestAttrDetail(facilitiesAttrDetailsMapper);

            facilitiesAttrDetailsMapper.setKey("arborQuantity");
            facilitiesAttrDetailsMapper.setDataValStr("141,55,29,99,17,146,43,17,46");
            facilitiesAttrDetailsMapper.setKeyDesc("数量（株）");
            demoTestMapper.insertTestAttrDetail(facilitiesAttrDetailsMapper);

            facilitiesAttrDetailsMapper.setKey("treeHeight");
            facilitiesAttrDetailsMapper.setDataValStr(", ,≥2, , , ,≥2, ,≥6");
            facilitiesAttrDetailsMapper.setKeyDesc("树高");
            demoTestMapper.insertTestAttrDetail(facilitiesAttrDetailsMapper);

            facilitiesAttrDetailsMapper.setKey("Diameter");
            facilitiesAttrDetailsMapper.setDataValStr("≥15.1,≥15,≥12,≥18,≥18,≥12, , , ");
            facilitiesAttrDetailsMapper.setKeyDesc("胸径");
            demoTestMapper.insertTestAttrDetail(facilitiesAttrDetailsMapper);

            facilitiesAttrDetailsMapper.setKey("type");
            facilitiesAttrDetailsMapper.setDataValStr("3,3,1,3,3,3,1, ,3");
            facilitiesAttrDetailsMapper.setKeyDesc("类型");
            demoTestMapper.insertTestAttrDetail(facilitiesAttrDetailsMapper);

        }
        return null;
    }


    public Object testAddTxcData(FacilitiesMinEight facilitiesMin) {
        FacilitiesMinEight facilitiesMinEight = demoTestMapper.queryFacilitiesMinByMinCodeAndProjectCode(facilitiesMin);
        if (facilitiesMinEight != null){
            return null;
        }
        return demoTestMapper.insertTestFacilitiesEightData(facilitiesMin);
    }

    public Object testAddProject(Project project) {
        Project project1 = demoTestMapper.queryProjectByProjectCode(project.getProjectCode());
        if (project1 != null){
            return "数据库中已有该项目，添加失败";
        }
        demoTestMapper.testAddProjectToUser(project.getProjectCode(),"f99386ab82e842fe8c6af0d419e2789a");
        return demoTestMapper.testAddProject(project);
    }
}
