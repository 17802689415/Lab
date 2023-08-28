package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.it.pojo.*;
import com.it.service.ConsignorInfoService;
import com.it.service.SampleInfoService;
import com.it.service.SampleTestInfoService;
import com.it.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lab")
public class SampleCaseController {

    @Autowired
    private ConsignorInfoService consignorInfoService;
    @Autowired
    private SampleInfoService sampleInfoService;
    @Autowired
    private SampleTestInfoService sampleTestInfoService;
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
    @PostMapping("/postCase")
    @Transactional
    public R postCase(@Valid @RequestBody CaseCommon caseCommon){

        caseCommon.getConsignorInfo().setCaseNum(caseCommon.getCaseNum());
        caseCommon.getSampleInfo().setCaseNum(caseCommon.getCaseNum());
        caseCommon.getSampleInfo().setStatus("待收样");
        for (SampleTestInfo sampleTestInfo:caseCommon.getSampleTestInfo()) {
            sampleTestInfo.setCaseNum(caseCommon.getCaseNum());
        }
        boolean saveConsignor = consignorInfoService.save(caseCommon.getConsignorInfo());
        boolean saveSample = sampleInfoService.save(caseCommon.getSampleInfo());
        boolean saveSampleTest = sampleTestInfoService.saveBatch(caseCommon.getSampleTestInfo());

        if(saveConsignor&&saveSample&&saveSampleTest){
            return R.success("success",200);
        }
        return R.error("error",100);
    }
    @GetMapping("/getSampleInfo")
    public R getSampleInfo(){
        LambdaQueryWrapper<SampleInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SampleInfo::getStatus,"待收样");
        List<SampleInfo> list = sampleInfoService.list(queryWrapper);
        return R.success(list,200);
    }
    @PostMapping("/getSampleTestItemListByCaseNum")
    public R getSampleTestItemListByCaseNum(String caseNum){
        LambdaQueryWrapper<SampleTestInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SampleTestInfo::getCaseNum,caseNum);
        List<SampleTestInfo> list = sampleTestInfoService.list(queryWrapper);
        return R.success(list,200);
    }
    @PostMapping("/receiveSample")
    public R receiveSample(@RequestBody SampleInfo sampleInfo){
        UpdateWrapper<SampleInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("case_num",sampleInfo.getCaseNum())
                .set("sample_taker",sampleInfo.getSampleTaker())
                .set("receive_date",sampleInfo.getReceiveDate())
                .set("status","已收样");
        boolean update = sampleInfoService.update(updateWrapper);
        if (update){
            return R.success("success",200);
        }
        return R.error("error",100);
    }
    @GetMapping("/getTask")
    public R getTask(){
        LambdaQueryWrapper<SampleInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNotNull(SampleInfo::getSampleTaker);
        List<SampleInfo> list = sampleInfoService.list(queryWrapper);
        List<SampleInfo> newList = new ArrayList<>();
        for (SampleInfo sampleInfo : list) {
            LambdaQueryWrapper<SampleTestInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(SampleTestInfo::getCaseNum,sampleInfo.getCaseNum())
                    .isNull(SampleTestInfo::getStatus);
            List<SampleTestInfo> sampleTestInfoList = sampleTestInfoService.list(lambdaQueryWrapper);
            sampleInfo.setSampleTestInfoList(sampleTestInfoList);
            if (sampleTestInfoList.size() == 0){
                newList.add(sampleInfo);
            }
        }
        for (SampleInfo s : newList) {
            list.remove(s);
        }
        return R.success(list,200);
    }
    @PostMapping("/receiveTask")
    @Transactional
    public R receiveTask(@RequestBody TaskCommon taskCommon){
        System.out.println(taskCommon.getType());
        System.out.println(taskCommon.getType() == "样品");
        if("样品".equals(taskCommon.getType())){

            for (CheckedCommon c : taskCommon.getCheckedList()) {
                UpdateWrapper<SampleTestInfo> wrapper = new UpdateWrapper<>();
                wrapper.eq("case_num",c.getCaseNum())
                        .set("status","待检测")
                        .set("inspector",taskCommon.getInspector());
                boolean update = sampleTestInfoService.update(wrapper);
                if(!update){
                    return R.error("error",100);
                }
            }
        } else if ("测试项目".equals(taskCommon.getType())) {
            for (CheckedCommon c : taskCommon.getCheckedList()) {
                UpdateWrapper<SampleTestInfo> wrapper = new UpdateWrapper<>();
                wrapper.eq("case_num",c.getCaseNum())
                        .eq("test_item",c.getValue())
                        .set("status","待检测")
                        .set("inspector",taskCommon.getInspector());
                boolean update = sampleTestInfoService.update(wrapper);
                if(!update){
                    return R.error("error",100);
                }
            }
        }
        return null;
    }

    @PostMapping("/getMyTask")
    public R getMyTask(@RequestBody TaskCommon taskCommon){
        LambdaQueryWrapper<SampleTestInfo> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(SampleTestInfo::getInspector,taskCommon.getInspector())
                .eq(SampleTestInfo::getStatus,taskCommon.getType());
        List<SampleTestInfo> list = sampleTestInfoService.list(queryWrapper);
        for (SampleTestInfo s :
                list) {
            LambdaQueryWrapper<SampleInfo> sampleWrapper = new LambdaQueryWrapper<>();
            sampleWrapper.eq(SampleInfo::getCaseNum,s.getCaseNum());
            SampleInfo sampleInfo = sampleInfoService.getOne(sampleWrapper);
            s.setSampleInfo(sampleInfo);
            LambdaQueryWrapper<ConsignorInfo> consignorWrapper = new LambdaQueryWrapper<>();
            consignorWrapper.eq(ConsignorInfo::getCaseNum,s.getCaseNum());
            ConsignorInfo consignorInfo = consignorInfoService.getOne(consignorWrapper);
            s.setConsignorInfo(consignorInfo);
        }
        return R.success(list,200);
    }
}
