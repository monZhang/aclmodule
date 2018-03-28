package com.acl.controller;

import com.acl.params.PageParam;
import com.acl.VO.PageResult;
import com.acl.VO.JsonData;
import com.acl.entity.SysUser;
import com.acl.params.UserParam;
import com.acl.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by 65766 on 2018/1/28.
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService userService;

    @PostMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam param) {
        userService.saveUser(param);
        return JsonData.success();
    }

    @PostMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param) {
        userService.updateUser(param);
        return JsonData.success();
    }


    @GetMapping("/page.json")
    @ResponseBody
    public JsonData toUserPage(@RequestParam(value = "deptId") Integer deptId, PageParam param) {
        final PageResult<SysUser> pageResult = userService.userPage(deptId, param);
        return JsonData.success(pageResult);
    }


}
