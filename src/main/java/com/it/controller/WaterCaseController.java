package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.it.pojo.*;
import com.it.service.ConsignorInfoService;
import com.it.service.WaterTestInfoService;
import com.it.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/water")
public class WaterCaseController {
    @Autowired
    private WaterTestInfoService waterTestInfoService;
    @Autowired
    private ConsignorInfoService consignorInfoService;
    @PostMapping("/postCase")
    @Transactional
    public R postCase(@Valid @RequestBody CaseCommon caseCommon){
        System.out.println(caseCommon);
        caseCommon.getConsignorInfo().setCaseNum(caseCommon.getCaseNum());
        for (WaterTestInfo waterTestInfo:caseCommon.getWaterTestInfo()) {
            waterTestInfo.setCaseNum(caseCommon.getCaseNum());
            waterTestInfo.setStatus("待收样");
            waterTestInfo.setSampleType(caseCommon.getSampleType());
        }
        boolean saveConsignor = consignorInfoService.save(caseCommon.getConsignorInfo());
        boolean saveWaterTest = waterTestInfoService.saveBatch(caseCommon.getWaterTestInfo());

        if(saveConsignor&&saveWaterTest){
            return R.success("success",200);
        }
        return R.error("error",100);
    }
    @GetMapping("/getWaterCase")
    public R getWaterCase(){
        List<WaterCase> list = new ArrayList<>();
        List<WaterTestInfo> caseList = waterTestInfoService.getCaseList("待收样");
        for (WaterTestInfo info : caseList) {
            LambdaQueryWrapper<WaterTestInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WaterTestInfo::getCaseNum,info.getCaseNum());
            List<WaterTestInfo> waterTestInfos = waterTestInfoService.list(queryWrapper);
            WaterCase waterCase = new WaterCase();
            waterCase.setCaseNum(info.getCaseNum());
            waterCase.setCreateTime(info.getCreateTime());
            waterCase.setList(waterTestInfos);
            list.add(waterCase);
        }
        return R.success(list,200);
    }
    @PostMapping("/getWaterTestItemListByCaseNum")
    public R getWaterTestItemListByCaseNum(String caseNum){
        LambdaQueryWrapper<WaterTestInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WaterTestInfo::getCaseNum,caseNum);
        List<WaterTestInfo> waterTestInfos = waterTestInfoService.list(queryWrapper);
        return R.success(waterTestInfos,200);
    }
    @PostMapping("/receiveSample")
    public R receiveSample(@RequestBody WaterTestInfo waterTestInfo){
        UpdateWrapper<WaterTestInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("case_num",waterTestInfo.getCaseNum())
                .set("sample_taker",waterTestInfo.getSampleTaker())
                .set("receive_date",waterTestInfo.getReceiveDate())
                .set("status","已收样");
        boolean update = waterTestInfoService.update(updateWrapper);
        if(update){
            return R.success("success",200);
        }
        return R.error("error",100);
    }
    @PostMapping("/rejectSample")
    public R rejectSample(@RequestBody WaterTestInfo waterTestInfo){
        UpdateWrapper<WaterTestInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("case_num",waterTestInfo.getCaseNum())
                .set("status","拒绝收样")
                .set("reject_reason",waterTestInfo.getRejectReason());
        boolean update = waterTestInfoService.update(updateWrapper);
        if (update){
            return R.success("success",200);
        }
        return R.error("error",100);
    }
    @PostMapping("/receiveTask")
    public R receiveTask(@RequestBody TaskCommon taskCommon){
        if("样品".equals(taskCommon.getType())){
            for (CheckedCommon c :
                    taskCommon.getCheckedList()) {
                UpdateWrapper<WaterTestInfo> wrapper = new UpdateWrapper<>();
                wrapper.eq("case_num",c.getCaseNum())
                        .set("status","待检测")
                        .set("inspector",taskCommon.getInspector());
                boolean update = waterTestInfoService.update(wrapper);
                if (!update){
                    return R.error("error",100);
                }
            }
        } else if ("测试项目".equals(taskCommon.getType())) {
            for (CheckedCommon c : taskCommon.getCheckedList()) {
                UpdateWrapper<WaterTestInfo> wrapper = new UpdateWrapper<>();
                wrapper.eq("case_num",c.getCaseNum())
                        .eq("test_item",c.getValue())
                        .set("status","待检测")
                        .set("inspector",taskCommon.getInspector());
                boolean update = waterTestInfoService.update(wrapper);
                if(!update){
                    return R.error("error",100);
                }
            }
        }
        return R.success("success",200);
    }
    @PostMapping("/startTask")
    public R startTask(@RequestBody WaterTestInfo waterTestInfo){
        UpdateWrapper<WaterTestInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code",waterTestInfo.getCode())
                .set("status","检测中");
        boolean update = waterTestInfoService.update(updateWrapper);
        if (update){
            return R.success("success",200);
        }
        return R.error("error",100);
    }

}
