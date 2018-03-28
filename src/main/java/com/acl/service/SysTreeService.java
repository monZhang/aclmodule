package com.acl.service;

import com.acl.dao.SysAclMapper;
import com.acl.dao.SysAclModuleMapper;
import com.acl.dao.SysDepartMapper;
import com.acl.dto.AclDto;
import com.acl.dto.AclModuleDto;
import com.acl.dto.DeptLevelDto;
import com.acl.entity.SysAcl;
import com.acl.entity.SysAclModule;
import com.acl.entity.SysDepart;
import com.acl.utils.LevelUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 65766 on 2018/1/21.
 */
@Service
@Transactional
public class SysTreeService {

    @Resource
    private SysDepartMapper sysDepartMapper;
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysCoreService sysCoreService;


    //部门树
    public List<DeptLevelDto> deptTree() {
        List<SysDepart> deptList = sysDepartMapper.selectAll();
        List<DeptLevelDto> deptDtoList = Lists.newArrayList();
        for (SysDepart dept : deptList) {
            deptDtoList.add(DeptLevelDto.adapt(dept));
        }
        return createDeptTree(deptDtoList);
    }

    private List<DeptLevelDto> createDeptTree(List<DeptLevelDto> deptDtoList) {
        if (CollectionUtils.isEmpty(deptDtoList)) {
            return Lists.newArrayList();
        }

        Multimap<String, DeptLevelDto> deptLevelDtoMultimap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();
        for (DeptLevelDto dld : deptDtoList) {
            deptLevelDtoMultimap.put(dld.getLevel(), dld);
            if (LevelUtil.ROOT.equals(dld.getLevel())) {
                rootList.add(dld);
            }
        }
        Collections.sort(rootList, comparator);
        toDeptTree(deptLevelDtoMultimap, LevelUtil.ROOT, rootList);
        return rootList;
    }

    private void toDeptTree(Multimap<String, DeptLevelDto> deptLevelDtoMultimap, String rootLevel, List<DeptLevelDto> rootList) {
        for (DeptLevelDto dld : rootList) {
            //获取下一级level
            String nextLevel = LevelUtil.calculateLevel(rootLevel, dld.getId());
            List<DeptLevelDto> deptLevelDtos = (List<DeptLevelDto>) deptLevelDtoMultimap.get(nextLevel);
            Collections.sort(deptLevelDtos, comparator);
            dld.setDeptList(deptLevelDtos);
            toDeptTree(deptLevelDtoMultimap, nextLevel, deptLevelDtos);
        }
    }

    //按照seq排序
    Comparator<DeptLevelDto> comparator = new Comparator<DeptLevelDto>() {

        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    //------------------------------------------------
    //权限模块树
    public List<AclModuleDto> aclModuleTree() {
        List<SysAclModule> deptList = sysAclModuleMapper.selectAll();
        List<AclModuleDto> aclModuleDtoList = Lists.newArrayList();
        for (SysAclModule aclModule : deptList) {
            aclModuleDtoList.add(AclModuleDto.adapt(aclModule));
        }
        return createAclModuleTree(aclModuleDtoList);
    }


    private List<AclModuleDto> createAclModuleTree(List<AclModuleDto> aclModuleDtoList) {
        if (CollectionUtils.isEmpty(aclModuleDtoList)) {
            return Lists.newArrayList();
        }

        Multimap<String, AclModuleDto> aclModuleDtoMultimap = ArrayListMultimap.create();
        List<AclModuleDto> rootList = Lists.newArrayList();
        for (AclModuleDto ald : aclModuleDtoList) {
            aclModuleDtoMultimap.put(ald.getLevel(), ald);
            if (LevelUtil.ROOT.equals(ald.getLevel())) {
                rootList.add(ald);
            }
        }
        Collections.sort(rootList, aclModuleComparator);
        toAclModuleTree(aclModuleDtoMultimap, LevelUtil.ROOT, rootList);
        return rootList;
    }

    private void toAclModuleTree(Multimap<String, AclModuleDto> aclModuleDtoMultimap, String rootLevel, List<AclModuleDto> rootList) {
        for (AclModuleDto acl : rootList) {
            //获取下一级level
            String nextLevel = LevelUtil.calculateLevel(rootLevel, acl.getId());
            List<AclModuleDto> aclModuleDtoList = (List<AclModuleDto>) aclModuleDtoMultimap.get(nextLevel);
            Collections.sort(aclModuleDtoList, aclModuleComparator);
            acl.setAclModuleList(aclModuleDtoList);
            toAclModuleTree(aclModuleDtoMultimap, nextLevel, aclModuleDtoList);
        }
    }

    //按照seq排序
    Comparator<AclModuleDto> aclModuleComparator = new Comparator<AclModuleDto>() {
        public int compare(AclModuleDto o1, AclModuleDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


    //----------------------------
    //角色权限树
    public List<AclModuleDto> roleTree(Integer roleId) {
        //获取所有权限
        List<SysAcl> allAclList = sysAclMapper.getAll();
        if (CollectionUtils.isEmpty(allAclList)) {
            return Lists.newArrayList();
        }
        //获取当前登录用户所有权限(所有角色的权限)
        List<SysAcl> currentUserAcl = sysCoreService.getCurrentUserAcl();

        //获取当前角色的所有权限
        List<SysAcl> roleAcl = sysCoreService.getRoleAcl(roleId);

        //获取aclModule树
        final List<AclModuleDto> aclModuleDtoList = aclModuleTree();

        //设置是否选中, 是否有权限修改
        Multimap<Integer, AclDto> aclDtoMap = ArrayListMultimap.create();
        for (SysAcl acl : allAclList) {
            AclDto aclDto = AclDto.adapt(acl);
            //重写acl hashCode和Equals方法(如果id相同既相同)
            if (currentUserAcl.contains(acl)) {
                aclDto.setHasAcl(true);
            }
            if (roleAcl.contains(acl)) {
                aclDto.setChecked(true);
            }
            aclDtoMap.put(aclDto.getAclModuleId(), aclDto);
        }

        //绑定权限当权限模块上
        bindAcl2AclModule(aclDtoMap, aclModuleDtoList);

        return aclModuleDtoList;
    }

    private void bindAcl2AclModule(Multimap aclDtoMap, List<AclModuleDto> aclModuleDtoList) {
        if (CollectionUtils.isEmpty(aclModuleDtoList)) {
            return;
        }
        for (AclModuleDto aclModuleDto : aclModuleDtoList) {
            List<AclDto> aclDtoList = (List<AclDto>) aclDtoMap.get(aclModuleDto.getId());
            Collections.sort(aclDtoList, aclComparator);
            aclModuleDto.setAclList(aclDtoList);
            bindAcl2AclModule(aclDtoMap, aclModuleDto.getAclModuleList());
        }
    }

    //按照seq排序
    Comparator<AclDto> aclComparator = new Comparator<AclDto>() {
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

}

