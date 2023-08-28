package com.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.mapper.SampleInfoMapper;
import com.it.mapper.SampleTestInfoMapper;
import com.it.pojo.SampleInfo;
import com.it.pojo.SampleTestInfo;
import com.it.service.SampleInfoService;
import com.it.service.SampleTestInfoService;
import org.springframework.stereotype.Service;

@Service
public class SampleTestInfoServiceImpl extends ServiceImpl<SampleTestInfoMapper, SampleTestInfo> implements SampleTestInfoService {


}
