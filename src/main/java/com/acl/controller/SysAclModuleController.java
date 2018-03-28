package com.acl.controller;

import com.acl.VO.JsonData;
import com.acl.dto.AclModuleDto;
import com.acl.params.AclModuleParam;
import com.acl.service.SysAclModultService;
import com.acl.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Resource
    private SysAclModultService sysAclModultService;

    @Resource
    private SysTreeService treeService;

    @PostMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam param) {
        sysAclModultService.saveAclModule(param);
        return JsonData.success();
    }

    @PostMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param) {
        sysAclModultService.updateAclModule(param);
        return JsonData.success();
    }

    @GetMapping("/delete.json")
    @ResponseBody
    public JsonData deleteAclModule(Integer id) {
        sysAclModultService.deleteAclModule(id);
        return JsonData.success();
    }

    @GetMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        final List<AclModuleDto> deptLevelDtos = treeService.aclModuleTree();
        return JsonData.success(deptLevelDtos);
    }


    @GetMapping("/acl.page")
    public ModelAndView toAclModulePage() {
        final ModelAndView view = new ModelAndView("acl");
        return view;
    }

}
