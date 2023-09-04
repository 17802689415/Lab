package com.it.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.config.LdapConfiguration;
import com.it.mapper.ConsignorInfoMapper;
import com.it.mapper.UserInfoMapper;
import com.it.pojo.ConsignorInfo;
import com.it.pojo.LdapUser;
import com.it.pojo.Login;
import com.it.pojo.UserInfo;
import com.it.service.ConsignorInfoService;
import com.it.service.UserInfoService;
import com.it.utils.JwtTokenUtils;
import com.it.utils.R;
import com.it.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConsignorInfoServiceImpl extends ServiceImpl<ConsignorInfoMapper, ConsignorInfo> implements ConsignorInfoService {
    @Autowired
    private ConsignorInfoMapper consignorInfoMapper;



    @Override
    public List<ConsignorInfo> getApplyDataBySampleStatus(ConsignorInfo consignorInfo) {
//        Page page = new Page<>(consignorInfo.getCurrentPage(),consignorInfo.getPageSize());
        int val = (consignorInfo.getCurrentPage()-1)*consignorInfo.getPageSize();
        consignorInfo.setCurrentPage(val);
        List<ConsignorInfo> data = consignorInfoMapper.getApplyDataBySampleStatus(consignorInfo);

        return data;
    }
}
