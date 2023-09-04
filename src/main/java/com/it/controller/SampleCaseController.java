package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@RequestMapping("/sample")
public class SampleCaseController {

    @Autowired
    private ConsignorInfoService consignorInfoService;
    @Autowired
    private SampleInfoService sampleInfoService;
    @Autowired
    private SampleTestInfoService sampleTestInfoService;

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

    @PostMapping("/receiveTask")
    @Transactional
    public R receiveTask(@RequestBody TaskCommon taskCommon){
        System.out.println(taskCommon.getType());
        System.out.println(taskCommon.getType() == "样品");
        System.out.println(taskCommon.getCheckedList());
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
        return R.success("success",200);
    }

    @PostMapping("/startTask")
    public R startTask(@RequestBody SampleTestInfo sampleTestInfo){
        UpdateWrapper<SampleTestInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code",sampleTestInfo.getCode())
                .set("status","检测中");
        boolean update = sampleTestInfoService.update(updateWrapper);
        if (update){
            return R.success("success",200);
        }
        return R.error("error",100);
    }

    @PostMapping("/rejectSample")
    public R rejectSample(@RequestBody SampleInfo sampleInfo){
        UpdateWrapper<SampleInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("case_num",sampleInfo.getCaseNum())
                .set("status","拒绝收样")
                .set("reject_reason",sampleInfo.getRejectReason());
        boolean update = sampleInfoService.update(updateWrapper);
        if(update){
            return R.success("success",200);
        }
        return R.error("error",100);
    }


}
