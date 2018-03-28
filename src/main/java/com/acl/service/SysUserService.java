package com.acl.service;

import com.acl.params.PageParam;
import com.acl.VO.PageResult;
import com.acl.common.RequestHolder;
import com.acl.dao.SysUserMapper;
import com.acl.entity.SysUser;
import com.acl.exception.ParamException;
import com.acl.params.UserParam;
import com.acl.utils.BeanValidator;
import com.acl.utils.IpUtil;
import com.acl.utils.MD5Util;
import com.acl.utils.PasswordUtil;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by 65766 on 2018/1/28.
 */
@Service
@Transactional
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 保存用户
     *
     * @param param
     */
    public void saveUser(UserParam param) {
        BeanValidator.check(param);
        if (checkPhone(param.getTelephone(), param.getId())) {
            throw new ParamException("手机号码已经存在");
        }
        if (checkMail(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已经存在");
        }

        SysUser user = SysUser.builder().username(param.getUsername()).phone(param.getTelephone()).email(param.getMail()).
                status(param.getStatus()).departId(param.getDeptId()).remark(param.getRemark()).build();
        //设置密码
        String originalPassword = PasswordUtil.generatPassword();
        originalPassword = "123456";

        String password = MD5Util.encrypt(originalPassword);
        // TODO: 发送邮件
        user.setPassword(password);
        user.setOperator(RequestHolder.getUser().getUsername());
        user.setOperatorTime(new Date());
        user.setOperatorIp(IpUtil.getUserIP(RequestHolder.getRequest()));
        sysUserMapper.insertSelective(user);
    }


    /**
     * 修改用户(后台使用)
     *
     * @param param
     */
    public void updateUser(UserParam param) {
        BeanValidator.check(param);
        if (checkPhone(param.getTelephone(), param.getId())) {
            throw new ParamException("手机号码已经存在");
        }
        if (checkMail(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已经存在");
        }
        final SysUser sysUser = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(sysUser, "待修改的用户不存在");
        SysUser user = SysUser.builder().id(param.getId()).username(param.getUsername()).phone(param.getTelephone()).email(param.getMail()).
                status(param.getStatus()).departId(param.getDeptId()).remark(param.getRemark()).build();
        user.setOperator(RequestHolder.getUser().getUsername());
        user.setOperatorTime(new Date());
        user.setOperatorIp(IpUtil.getUserIP(RequestHolder.getRequest()));
        sysUserMapper.updateByPrimaryKeySelective(user);
    }


    //检查邮箱是否存在
    private boolean checkMail(String email, Integer userId) {
        int i = sysUserMapper.checkMail(email, userId);
        return i > 0;
    }

    //检查手机号是否存在
    private boolean checkPhone(String phone, Integer userId) {
        int i = sysUserMapper.checkPhone(phone, userId);
        return i > 0;
    }

    /**
     * 根据用户名查询用户对象
     *
     * @param userName
     * @return
     */
    public SysUser selectByKeyWord(String userName) {
        return sysUserMapper.selectByKeyWord(userName);
    }

    public PageResult userPage(Integer deptId, PageParam param) {
        int totalCount = sysUserMapper.selectCountByDeptId(deptId);
        if (totalCount > 0) {
            List<SysUser> data = sysUserMapper.userPage(deptId, param);
            return PageResult.<SysUser>builder().data(data).total(totalCount).build();
        }
        return PageResult.<SysUser>builder().build();
    }
}
