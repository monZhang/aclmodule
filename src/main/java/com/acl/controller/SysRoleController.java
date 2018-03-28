package com.acl.controller;

import com.acl.VO.JsonData;
import com.acl.dto.AclModuleDto;
import com.acl.entity.SysRole;
import com.acl.params.RoleParam;
import com.acl.service.SysRoleService;
import com.acl.service.SysTreeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 65766 on 2018/2/3.
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysTreeService sysTreeService;

    @GetMapping("/role.page")
    @ResponseBody
    public ModelAndView page() {
        final ModelAndView view = new ModelAndView("role");
        return view;
    }

    @PostMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(RoleParam param) {
        sysRoleService.saveAcl(param);
        return JsonData.success();
    }

    @PostMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(RoleParam param) {
        sysRoleService.updateAcl(param);
        return JsonData.success();
    }

    @GetMapping("/list.json")
    @ResponseBody
    public JsonData getAllRole() {
        final List<SysRole> allRole = sysRoleService.getAllRole();
        return JsonData.success(allRole);
    }

    @PostMapping("/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@Param("roleId") Integer roleId) {
        List<AclModuleDto> data = sysTreeService.roleTree(roleId);
        return JsonData.success(data);
    }

    @PostMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@Param("roleId") Integer roleId) {
        List<AclModuleDto> data = sysTreeService.roleTree(roleId);
        return JsonData.success(data);
    }


}
