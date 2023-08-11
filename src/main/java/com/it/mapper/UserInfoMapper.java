package com.it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.pojo.Login;
import com.it.pojo.PageCommon;
import com.it.pojo.UserInfo;
import com.it.utils.R;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    R login(Login log);
}
