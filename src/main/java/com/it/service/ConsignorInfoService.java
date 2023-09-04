package com.it.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.pojo.ConsignorInfo;
import com.it.pojo.Login;
import com.it.pojo.UserInfo;
import com.it.utils.R;

import java.util.List;

public interface ConsignorInfoService extends IService<ConsignorInfo> {
    List<ConsignorInfo> getApplyDataBySampleStatus(ConsignorInfo consignorInfo);
}
