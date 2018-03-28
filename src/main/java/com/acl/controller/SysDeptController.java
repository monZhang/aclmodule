package com.acl.controller;

import com.acl.VO.JsonData;
import com.acl.dto.DeptLevelDto;
import com.acl.params.DeptParam;
import com.acl.service.SysDeptService;
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

/**
 * Created by 65766 on 2018/1/21.
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Resource
    private SysDeptService deptService;
    @Resource
    private SysTreeService treeService;

    @PostMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param) {
        deptService.saveDept(param);
        return JsonData.success();
    }

    @PostMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param) {
        deptService.updateDept(param);
        return JsonData.success();
    }

    @GetMapping("/delete.json")
    @ResponseBody
    public JsonData deleteDept(Integer id) {
        deptService.deleteDept(id);
        return JsonData.success();
    }

    @GetMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        final List<DeptLevelDto> deptLevelDtos = treeService.deptTree();
        return JsonData.success(deptLevelDtos);
    }


    @GetMapping("/dept.page")
    public ModelAndView toDeptPage() {
        final ModelAndView view = new ModelAndView("dept");
        return view;
    }


}
