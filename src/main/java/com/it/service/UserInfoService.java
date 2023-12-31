package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.pojo.Login;
import com.it.pojo.UserInfo;
import com.it.utils.R;

import javax.naming.NamingException;

public interface UserInfoService extends IService<UserInfo> {
    R login(Login log);

}
