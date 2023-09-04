package com.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.mapper.SampleTestInfoMapper;
import com.it.mapper.WaterTestInfoMapper;
import com.it.pojo.SampleTestInfo;
import com.it.pojo.WaterTestInfo;
import com.it.service.SampleTestInfoService;
import com.it.service.WaterTestInfoService;
import com.it.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaterTestInfoServiceImpl extends ServiceImpl<WaterTestInfoMapper, WaterTestInfo> implements WaterTestInfoService {
    @Autowired
    private WaterTestInfoMapper waterTestInfoMapper;

    @Override
    public List<WaterTestInfo> getCaseList(String status) {
        List<WaterTestInfo> caseList = waterTestInfoMapper.getCaseList(status);
        return caseList;
    }
}
