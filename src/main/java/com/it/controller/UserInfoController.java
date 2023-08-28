package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.pojo.Login;
import com.it.pojo.UserInfo;
import com.it.pojo.UserPageCommon;
import com.it.service.UserInfoService;
import com.it.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/lab")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @PostMapping("/getUserData")
    public R<Page> getUserData(UserPageCommon pageCommon){
        Page page = new Page<>(pageCommon.getCurrentPage(),pageCommon.getPageSize());
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(UserInfo::getActive);
        if (pageCommon.getEmpId().isEmpty()){
            Page userData = userInfoService.page(page,queryWrapper);
            return R.success(userData,200);
        }else {
            queryWrapper.eq(UserInfo::getEmpId,pageCommon.getEmpId());
            Page userDataById = userInfoService.page(page, queryWrapper);
            return R.success(userDataById,200);
        }
    }
    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public R addUser(@Valid @RequestBody UserInfo userInfo){

        boolean save = userInfoService.save(userInfo);
        if(save){
            return R.success("添加成功",200);
        }
        return R.error("添加失败",100);
    }
    @PostMapping("/updateUser")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public R updateUser(@RequestBody UserInfo userInfo){

        boolean update = userInfoService.updateById(userInfo);
        if(update){
            return R.success("修改成功",200);
        }
        return R.error("修改失败",100);
    }
    @PostMapping("/deleteUser")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public R deleteUser(@RequestBody UserInfo userInfo){
        userInfo.setActive(false);
        boolean remove = userInfoService.updateById(userInfo);
        if(remove){
            return R.success("删除成功",200);
        }
        return R.error("删除失败",100);
    }
    @PostMapping("/backupUser")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public R backupUser(@RequestBody UserInfo userInfo){
        System.out.println(userInfo);
        boolean update = userInfoService.updateById(userInfo);
        if(update){
            return R.success("back up成功",200);
        }
        return R.error("back up失败",100);

    }
    @PostMapping("/login")
    public R userManager(Login log){

        return userInfoService.login(log);
    }
}
