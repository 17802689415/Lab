package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.pojo.SampleTestInfo;
import com.it.pojo.WaterTestInfo;
import com.it.utils.R;

import java.util.List;

public interface WaterTestInfoService extends IService<WaterTestInfo> {

    List<WaterTestInfo> getCaseList(String status);
}
