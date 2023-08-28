package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.pojo.CaseCommon;
import com.it.pojo.SampleInfo;
import com.it.pojo.SampleTestInfo;
import com.it.service.SampleInfoService;
import com.it.service.SampleTestInfoService;
import com.it.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lab")
public class CommonController {
    @Autowired
    private SampleInfoService sampleInfoService;
    @Autowired
    private SampleTestInfoService sampleTestInfoService;
    @PostMapping("/getAllData")
    public R getAllData(@RequestBody CaseCommon caseCommon){
        Page<SampleInfo> page = new Page<>(caseCommon.getCurrentPage(),caseCommon.getPageSize());
        if("SampleTest".equals(caseCommon.getTestType())){
            LambdaQueryWrapper<SampleInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(SampleInfo::getCaseNum,caseCommon.getCaseNum());
            Page sample = sampleInfoService.page(page, queryWrapper);
            for (SampleInfo s : page.getRecords()) {
                LambdaQueryWrapper<SampleTestInfo> testInfoWrapper = new LambdaQueryWrapper<>();
                testInfoWrapper.eq(SampleTestInfo::getCaseNum,s.getCaseNum());
                List<SampleTestInfo> list = sampleTestInfoService.list(testInfoWrapper);
                s.setSampleTestInfoList(list);
                s.setTestType("SampleTest");
            }
            return R.success(sample,200);
        }
        return R.error("error",100);
    }
}
