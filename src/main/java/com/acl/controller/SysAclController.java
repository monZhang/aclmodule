package com.acl.controller;

import com.acl.VO.JsonData;
import com.acl.params.PageParam;
import com.acl.VO.PageResult;
import com.acl.dto.AclModuleDto;
import com.acl.entity.SysAcl;
import com.acl.params.AclParam;
import com.acl.service.SysAclService;
import com.acl.service.SysTreeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 65766 on 2018/2/3.
 */
@Controller
@RequestMapping("/sys/acl")
public class SysAclController {

    @Resource
    private SysAclService sysAclService;


    @PostMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(AclParam param) {
        sysAclService.saveAcl(param);
        return JsonData.success();
    }

    @PostMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(AclParam param) {
        sysAclService.updateAcl(param);
        return JsonData.success();
    }

    @GetMapping("/page.json")
    @ResponseBody
    public JsonData toUserPage(@RequestParam(value = "aclModuleId") Integer aclModuleId, PageParam param) {
        final PageResult<SysAcl> pageResult = sysAclService.aclPage(aclModuleId, param);
        return JsonData.success(pageResult);
    }

}
