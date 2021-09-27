package com.example.demo.service;

import com.example.demo.mybatis.mapper.DemoTestMapper;
import com.example.demo.quartz.AllSearch;
import com.example.demo.quartz.FacilitiesMid;
import com.example.demo.quartz.FacilitiesMin;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class AsyncService {

    @Autowired
    private DemoTestMapper demoTestMapper;

    /**
     * 分组异步取小类设施
     *  先根据设施中类类型分组，再调用此方法，在此方法中所有的小类的设施中类类型相同
     */

    @Async("asyncExecutor")
    public Future<List<FacilitiesMin>> asyncFacilitieTransferMinByGroup(AllSearch allSearch,String facilitiesCode){
        // 根据中类类型查询出所有空间数据
        // 非空校验
        if (null == allSearch ){
            return null;
        }
        // 创建返回对象
        List<FacilitiesMin> facilitiesMins = new ArrayList<>();

        List<FacilitiesMid> facilitiesMidList = demoTestMapper.queryFacilitiesByProjectIdAndTypeRight(allSearch,"min",facilitiesCode);

        for (FacilitiesMid facilitiesMid : facilitiesMidList) {
            FacilitiesMin facilitiesMin = new FacilitiesMin();
            if (facilitiesMid.getFacilitiesCode().length() > 4){
                facilitiesMin.setMidTypeCode(facilitiesMid.getFacilitiesCode().substring(1,3));
            }
            BeanUtils.copyProperties(facilitiesMid,facilitiesMin);
            facilitiesMins.add(facilitiesMin);
        }
        return new AsyncResult<List<FacilitiesMin>>(facilitiesMins);


//        // 空间数据类型
//        String midTypeCode = outFacilitieList.get(0).getMidTypeCode();
//        // 定义小类资产list
//        Map<String, ShjZcflSsxl> zcflMap = null;
//        // 项目idList
//        Map<String, Integer> projectMap = null;
//        // 定义空间数据list
//        Map<String, FacilitiesGeo> geoMap = null;
//        // 中类设施类型存在
//        if (null != midTypeCode){
//            // 根据中类类型查询出所有小类资产
//            List<ShjZcflSsxl> shjZcflSsxlList = shjZcflSsxlDao.findAllBySSzlCode(midTypeCode);
//            // 将数据存在在map中
//            if (null != shjZcflSsxlList){
//                zcflMap = new HashMap<>();
//                for (ShjZcflSsxl shjZcflSsxl : shjZcflSsxlList) {
//                    zcflMap.put(shjZcflSsxl.getSsxlCode(), shjZcflSsxl);
//                }
//            }
//
//
//            // 查询项目编码
//            List<ShjgXmVo> projectList = shjgXmService.findAllByProjectCodeList(projectCodeList);
//            // 将数据存在在map中
//            if (null != projectList){
//                projectMap = new HashMap<>();
//                for (ShjgXmVo shjgXmVo : projectList) {
//                    projectMap.put(shjgXmVo.getProjectCode(), shjgXmVo.getProjectId());
//                }
//
//            }
//            // 设施小类编码集合
//            List<String> minCodeList = new ArrayList<>();
//            for (OutFacilitie outFacilitie : outFacilitieList) {
//                if (null != outFacilitie.getFacilitiesCode()) {
//                    minCodeList.add(outFacilitie.getFacilitiesCode());
//                }
//            }
//
//            long start1 = System.currentTimeMillis();
//            List<FacilitiesGeo> facilitiesMinGeoList = facilitiesCenter.findFacilitiesMinGeoList(projectCodeList, minCodeList, midTypeCode);
//            long end1 = System.currentTimeMillis();
//
//            log.info("线程名称-{},设施中类类型-{},时间-{}, 空间数据量-{}",Thread.currentThread().getName(), midTypeCode, end1 - start1, facilitiesMinGeoList==null?0:facilitiesMinGeoList.size());
//
//            if (null != facilitiesMinGeoList){
//                geoMap = new HashMap<>();
//                for (FacilitiesGeo facilitiesGeo : facilitiesMinGeoList) {
//                    // key 有项目id - 设施编码构成
//                    geoMap.put(facilitiesGeo.getProjectId() +"-"+facilitiesGeo.getCode(),facilitiesGeo);
//
//                }
//
//            }
//
//
//        }
//        // 设施中类类型为空 将分装之后的数据直接返回
//        // 分装属性信息
//        for (OutFacilitie outFacilitie : outFacilitieList) {
//            FacilitiesMin facilitiesMin = new FacilitiesMin();
//            list.add(facilitiesMin);
//            facilitiesMin.setXlssCode(outFacilitie.getFacilitiesCode());
//            facilitiesMin.setTypeCode(outFacilitie.getTypeCode());
//            facilitiesMin.setTypeVal(outFacilitie.getTypeVal());
//            facilitiesMin.setAddress(outFacilitie.getAddress());
//            facilitiesMin.setSvg(outFacilitie.getSvg());
//            facilitiesMin.setProjectCode(outFacilitie.getProjectCode());
//            // 查询Svg值
//            if (null != outFacilitie.getSvg() &&  "1".equals(outFacilitie.getSvg())){
//                facilitiesMin.setSvgUrl(getSvgUrl(outFacilitie.getFacilitiesCode()));
//            }
//            // 设施中类类型为空 小类资产,空间数据不需要添加
//            if (null == midTypeCode){
//                continue;
//            }
//            // 添加资产信息
//            if (null == zcflMap){
//                continue;
//            }
//            // 取出资产信息
//            ShjZcflSsxl shjZcflSsxl = zcflMap.get(outFacilitie.getTypeCode());
//            if (null != shjZcflSsxl){
//                facilitiesMin.setSsxlId(shjZcflSsxl.getSsxlId());
//                facilitiesMin.setSszlId(shjZcflSsxl.getSszlId());
//            }
//            // 取出项目id
//            if (null == projectMap){
//                continue;
//            }
//            Integer projectId = projectMap.get(outFacilitie.getProjectCode());
//            if (null == projectId){
//
//                continue;
//            }
//            if (null == geoMap){
//
//                continue;
//            }
//            FacilitiesGeo facilitiesGeo = geoMap.get(projectId+"-"+outFacilitie.getFacilitiesCode());
//            if (null != facilitiesGeo){
//
//                facilitiesMin.setSrid(facilitiesGeo.getVerRisd());
//                facilitiesMin.setGeom(facilitiesGeo.getGeom());
//                facilitiesMin.setZ(facilitiesGeo.getZ());
//                facilitiesMin.setZ1(facilitiesGeo.getZ1());
//            }
//
//        }
    }

}
