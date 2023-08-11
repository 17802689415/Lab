package com.it.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.pojo.Login;
import com.it.pojo.PageCommon;
import com.it.service.UserInfoService;
import com.it.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/lab")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @PostMapping("/getUserData")
    @ResponseBody
    public R<Page> getUserData(PageCommon pageCommon){
        Page page = new Page<>(pageCommon.getCurrentPage(),pageCommon.getPageSize());
        Page userData = userInfoService.page(page);
        return R.success(userData,200);
    }
    @PostMapping("/login")
    @ResponseBody
    public R userManager(Login log){

        return userInfoService.login(log);
    }
}
