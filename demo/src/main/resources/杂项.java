本机IP地址：192.168.0.161:83
服务项目地址：http://39.97.105.38:9090/euauth/#/projectList
服务账号：syq
服务密码：123qwe!@#

svn账号：qiduanyu
svn密码：essence_qdy

考勤OA网址：http://192.168.0.106/
账号：qiduanyu
密码：essence

=========
远程    
39.106.108.152   密码 Essence_2229
账号 administrator

maven打新jar包命令： mvn install:install-file -DgroupId=com.essence -DartifactId=euauth-client -Dversion=1.0-SNAPSHOT -Dpackaging=jar -Dfile=E:\repository\euauth-client-1.0-SNAPSHOT.jar
                    mvn install:install-file -DgroupId=com.essence -DartifactId=euauth-client -Dversion=1.0-SNAPSHOT -Dpackaging=jar -Dfile=E:\repository\euauth-client-1.0-SNAPSHOT-sources.jar
                    mvn install:install-file -DgroupId=com.essence.framework -DartifactId=essence-jpa -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -Dfile=E:\repository\essence-jpa-0.0.1-SNAPSHOT.jar
                    mvn install:install-file -DgroupId=com.essence.framework -DartifactId=essence-util -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -Dfile=E:\repository\essence-util-0.0.1-SNAPSHOT.jar
                    mvn install:install-file -DgroupId=com.essence.framework -DartifactId=essence-util -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -Dfile=E:\repository\essence-util-0.0.1-SNAPSHOT.jar
                    mvn install:install-file -DgroupId=com.essence.framework -DartifactId=essence-util -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -Dfile=E:\repository\essence-util-0.0.1-SNAPSHOT.jar
                    mvn install:install-file -DgroupId=com.aspose          -DartifactId=words   -Dversion=18.10-jdk16           -Dpackaging=jar -Dfile=E:\Downloads\aspose-words-15.8.0-jdk16.jar

wr_stp_q_month_report 数据表中月处理量计算公式：acc_w = 月天 * 设计处理能力 / 10000
设计处理能力是20000吨每天
2月  28*20000 = 560000吨处理能力
月处理量 单位是万m³  所以要用560000/10000 = 56

---
自己的项目地址：git@github.com:qiduanyu/demo.git

--
水安全电子水尺已经做完了
http://192.168.0.177/repository/editor?id=56&mod=258&itf=2520
后端的IP地址：192.168.0.161:82
你看你要是做完了 可以对接了给我说下



  /**
     * 获取所有摄像头信息并存入数据库
     * swjWeatherRegionSummary
     **/
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public SystemSecurityMessage swjWeatherRegionMax(@RequestParam String lgtd,@RequestParam String lttd,@RequestParam String xuhao,@RequestParam String didian) {
        Object geometryInfo = GisUtil.geometryBuffer(Double.valueOf(lgtd), Double.valueOf(lttd), "400");
        System.out.println(geometryInfo);
        List<String> cameraCodeList = GisUtil.cameraQueray(geometryInfo);
        String[] cameracodeArray = new String[cameraCodeList.size()];
        cameraCodeList.toArray(cameracodeArray);
        List<THdmisCamera> findByCameracodeIn = hdmisService.getTHdmisCameraInfo(cameracodeArray);
        List<TStationCamera> tStationCameras = new ArrayList<>();
        for (THdmisCamera tHdmisCamera : findByCameracodeIn) {
            TStationCamera tStationCamera = new TStationCamera();
            tStationCamera.setStcd(xuhao);
            tStationCamera.setStnm(didian);
            tStationCamera.setName(tHdmisCamera.getFixposition());
            tStationCamera.setPointX(tHdmisCamera.getPointX());
            tStationCamera.setPointY(tHdmisCamera.getPointY());
            tStationCamera.setSsjd(tHdmisCamera.getSsjd());
            tStationCamera.setCameraCode(tHdmisCamera.getCameracode());
            tStationCamera.setLocation(tHdmisCamera.getMonitorarea());
            tStationCameras.add(tStationCamera);
        }
        tStationCameraDao.saveAll(tStationCameras);
        return new SystemSecurityMessage("ok", "成功返回！", findByCameracodeIn);
    }