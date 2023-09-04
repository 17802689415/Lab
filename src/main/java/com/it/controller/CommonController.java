package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.pojo.*;
import com.it.service.ConsignorInfoService;
import com.it.service.SampleInfoService;
import com.it.service.SampleTestInfoService;
import com.it.service.WaterTestInfoService;
import com.it.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lab")
public class CommonController {
    @Autowired
    private SampleInfoService sampleInfoService;
    @Autowired
    private SampleTestInfoService sampleTestInfoService;
    @Autowired
    private ConsignorInfoService consignorInfoService;
    @Autowired
    private WaterTestInfoService waterTestInfoService;
    private static int counter = 0;
    @PostMapping("/getCaseNum")
    public R getCaseNum(@RequestBody String str){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);

        String code = timestamp + String.format("%04d", counter);
        counter++;
        return R.success(str.replace("=", "")+code,200);
    }
    @PostMapping("/getAllData")
    public R getAllData(@RequestBody CaseCommon caseCommon){
        Page<ConsignorInfo> page = new Page<>(caseCommon.getCurrentPage(),caseCommon.getPageSize());
        LambdaQueryWrapper<ConsignorInfo> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.like(ConsignorInfo::getCaseNum,caseCommon.getCaseNum())
                .orderByDesc(ConsignorInfo::getApplyDate);
        Page<ConsignorInfo> list = consignorInfoService.page(page,queryWrapper);
        for (ConsignorInfo info : list.getRecords()) {
            LambdaQueryWrapper<SampleInfo> sampleInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sampleInfoLambdaQueryWrapper.eq(SampleInfo::getCaseNum,info.getCaseNum());
            SampleInfo sampleInfo = sampleInfoService.getOne(sampleInfoLambdaQueryWrapper);
            info.setSampleInfo(sampleInfo);
            LambdaQueryWrapper<SampleTestInfo> sampleTestInfoLambdaQueryWrapper =new LambdaQueryWrapper<>();
            sampleTestInfoLambdaQueryWrapper.eq(SampleTestInfo::getCaseNum,info.getCaseNum());
            List<SampleTestInfo> sampleTestInfos = sampleTestInfoService.list(sampleTestInfoLambdaQueryWrapper);
            info.setSampleTestInfoList(sampleTestInfos);
            LambdaQueryWrapper<WaterTestInfo> waterTestInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            waterTestInfoLambdaQueryWrapper.eq(WaterTestInfo::getCaseNum,info.getCaseNum());
            List<WaterTestInfo> waterTestInfos = waterTestInfoService.list(waterTestInfoLambdaQueryWrapper);
            info.setWaterTestInfo(waterTestInfos);
        }
        return R.success(list,200);

    }
    @PostMapping("/getApplyDataByUser")
    public R getApplyDataByUser(@RequestBody ConsignorInfo consignorInfo){
        Page page = new Page<>(consignorInfo.getCurrentPage(),consignorInfo.getPageSize());
        LambdaQueryWrapper<ConsignorInfo> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(ConsignorInfo::getEmpId,consignorInfo.getEmpId())
                .like(ConsignorInfo::getCaseNum,consignorInfo.getCaseNum())
                .orderByDesc(ConsignorInfo::getApplyDate);
        Page<ConsignorInfo> list = consignorInfoService.page(page,queryWrapper);

            for (ConsignorInfo info : list.getRecords()) {
                LambdaQueryWrapper<SampleInfo> sampleInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
                sampleInfoLambdaQueryWrapper.eq(SampleInfo::getCaseNum,info.getCaseNum());
                SampleInfo sampleInfo = sampleInfoService.getOne(sampleInfoLambdaQueryWrapper);
                info.setSampleInfo(sampleInfo);
                LambdaQueryWrapper<SampleTestInfo> sampleTestInfoLambdaQueryWrapper =new LambdaQueryWrapper<>();
                sampleTestInfoLambdaQueryWrapper.eq(SampleTestInfo::getCaseNum,info.getCaseNum());
                List<SampleTestInfo> sampleTestInfos = sampleTestInfoService.list(sampleTestInfoLambdaQueryWrapper);
                info.setSampleTestInfoList(sampleTestInfos);
                LambdaQueryWrapper<WaterTestInfo> waterTestInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
                waterTestInfoLambdaQueryWrapper.eq(WaterTestInfo::getCaseNum,info.getCaseNum());
                List<WaterTestInfo> waterTestInfos = waterTestInfoService.list(waterTestInfoLambdaQueryWrapper);
                info.setWaterTestInfo(waterTestInfos);
            }
            return R.success(list,200);
    }

    @PostMapping("/getApplyDataBySampleStatus")
    public R getApplyDataBySampleStatus(@RequestBody ConsignorInfo consignorInfo){
        List<ConsignorInfo> list = consignorInfoService.getApplyDataBySampleStatus(consignorInfo);
        for (ConsignorInfo info : list) {
            LambdaQueryWrapper<SampleInfo> sampleInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sampleInfoLambdaQueryWrapper.eq(SampleInfo::getCaseNum,info.getCaseNum());
            SampleInfo sampleInfo = sampleInfoService.getOne(sampleInfoLambdaQueryWrapper);
            info.setSampleInfo(sampleInfo);
            LambdaQueryWrapper<SampleTestInfo> sampleTestInfoLambdaQueryWrapper =new LambdaQueryWrapper<>();
            sampleTestInfoLambdaQueryWrapper.eq(SampleTestInfo::getCaseNum,info.getCaseNum());
            List<SampleTestInfo> sampleTestInfos = sampleTestInfoService.list(sampleTestInfoLambdaQueryWrapper);
            info.setSampleTestInfoList(sampleTestInfos);
            LambdaQueryWrapper<WaterTestInfo> waterTestInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            waterTestInfoLambdaQueryWrapper.eq(WaterTestInfo::getCaseNum,info.getCaseNum());
            List<WaterTestInfo> waterTestInfos = waterTestInfoService.list(waterTestInfoLambdaQueryWrapper);
            info.setWaterTestInfo(waterTestInfos);
        }
        return R.success(list,200);
    }

    @GetMapping("/getTask")
    public R getTask(){
        LambdaQueryWrapper<SampleInfo> sampleQueryWrapper = new LambdaQueryWrapper<>();
        sampleQueryWrapper.isNotNull(SampleInfo::getSampleTaker);
        List<SampleInfo> samplelist = sampleInfoService.list(sampleQueryWrapper);
        List<SampleInfo> newSamplelist = new ArrayList<>();
        for (SampleInfo sampleInfo : samplelist) {
            LambdaQueryWrapper<SampleTestInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(SampleTestInfo::getCaseNum,sampleInfo.getCaseNum())
                    .isNull(SampleTestInfo::getStatus);
            List<SampleTestInfo> sampleTestInfoList = sampleTestInfoService.list(lambdaQueryWrapper);
            sampleInfo.setSampleTestInfoList(sampleTestInfoList);
            if (sampleTestInfoList.size() == 0){
                newSamplelist.add(sampleInfo);
            }
        }
        for (SampleInfo s : newSamplelist) {
            samplelist.remove(s);
        }

        List<WaterTestInfo> waterCaseList = waterTestInfoService.getCaseList("已收样");

        List<WaterCase> newWaterList = new ArrayList<>();
        for (WaterTestInfo waterInfo : waterCaseList) {
            LambdaQueryWrapper<WaterTestInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WaterTestInfo::getCaseNum,waterInfo.getCaseNum())
                    .eq(WaterTestInfo::getStatus,"已收样");
            List<WaterTestInfo> infos = waterTestInfoService.list(queryWrapper);
            WaterCase waterCase = new WaterCase();
            waterCase.setCaseNum(waterInfo.getCaseNum());
            waterCase.setCreateTime(waterInfo.getCreateTime());
            waterCase.setList(infos);
            newWaterList.add(waterCase);
        }

        AllTask allTask = new AllTask();
        allTask.setSampleInfoList(samplelist);
        allTask.setWaterTestInfoList(newWaterList);
        return R.success(allTask,200);
    }

    @PostMapping("/getMyTask")
    public R getMyTask(@RequestBody TaskCommon taskCommon){
        LambdaQueryWrapper<SampleTestInfo> sampleQueryWrapper =new LambdaQueryWrapper<>();
        sampleQueryWrapper.eq(SampleTestInfo::getInspector,taskCommon.getInspector())
                .eq(SampleTestInfo::getStatus,taskCommon.getType())
                .like(SampleTestInfo::getCode,taskCommon.getCaseNum());
        List<SampleTestInfo> sampleList = sampleTestInfoService.list(sampleQueryWrapper);
        for (SampleTestInfo s : sampleList) {
            LambdaQueryWrapper<SampleInfo> sampleWrapper = new LambdaQueryWrapper<>();
            sampleWrapper.eq(SampleInfo::getCaseNum,s.getCaseNum());
            SampleInfo sampleInfo = sampleInfoService.getOne(sampleWrapper);
            s.setSampleInfo(sampleInfo);
            LambdaQueryWrapper<ConsignorInfo> consignorWrapper = new LambdaQueryWrapper<>();
            consignorWrapper.eq(ConsignorInfo::getCaseNum,s.getCaseNum());
            ConsignorInfo consignorInfo = consignorInfoService.getOne(consignorWrapper);
            s.setConsignorInfo(consignorInfo);
        }
        LambdaQueryWrapper<WaterTestInfo> waterQueryWrapper = new LambdaQueryWrapper<>();
        waterQueryWrapper.eq(WaterTestInfo::getInspector,taskCommon.getInspector())
                .eq(WaterTestInfo::getStatus,taskCommon.getType())
                .like(WaterTestInfo::getCode,taskCommon.getCaseNum());
        List<WaterTestInfo> waterList = waterTestInfoService.list(waterQueryWrapper);
        for (WaterTestInfo w : waterList) {
            LambdaQueryWrapper<ConsignorInfo> consignorWrapper = new LambdaQueryWrapper<>();
            consignorWrapper.eq(ConsignorInfo::getCaseNum,w.getCaseNum());
            ConsignorInfo consignorInfo = consignorInfoService.getOne(consignorWrapper);
            w.setConsignorInfo(consignorInfo);
        }
        CaseCommon caseCommon = new CaseCommon();
        caseCommon.setSampleTestInfo(sampleList);
        caseCommon.setWaterTestInfo(waterList);
        return R.success(caseCommon,200);
    }
}
