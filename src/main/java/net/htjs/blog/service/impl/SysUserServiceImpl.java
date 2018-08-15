package net.htjs.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.dao.SysUserMapper;
import net.htjs.blog.dao.SysUserRoleMapper;
import net.htjs.blog.entity.BaseDomain;
import net.htjs.blog.entity.SysUser;
import net.htjs.blog.entity.SysUserRole;
import net.htjs.blog.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * backend/net.htjs.blog.service.impl
 *
 * @Description : 用户操作类，包含用户的CURD和用户的角色分配等
 * @Author : dingdongliang
 * @Date : 2018/4/17 10:33
 */
@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 根据用户名和密码查询对应的用户, 用于登录认证
     *
     * @param account 账号
     * @return net.htjs.blog.entity.SysUser
     * @author dingdongliang
     * @date 2018/4/12 17:45
     */
    @Override
    public SysUser userCertified(String account) {
        return sysUserMapper.userCertified(account);
    }

    /**
     * 给用户赋予角色，角色可以为多个
     *
     * @param userId  用户主键
     * @param roleIds 角色ID数组
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 8:33
     */
    @Override
    public void putRoleToUser(String userId, String[] roleIds) {
        sysUserRoleMapper.insertMany(userId, Arrays.asList(roleIds));
    }

    /**
     * 更新用户的角色,首先标记该用户的所有角色为过期，然后再配置新角色
     *
     * @param userId  用户主键
     * @param roleIds 角色ID集合
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 9:31
     */
    @Override
    public void updateRoleToUser(String userId, String[] roleIds) {
        //首先获取该用户现有的角色集合，挑出其中的roleId为A集合
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectByUserId(userId);
        List<String> aList = new ArrayList<>();
        for (SysUserRole sysUserRole : sysUserRoleList) {
            aList.add(sysUserRole.getRoleId());
        }

        //新角色数组roleIds为B集合，准备获取A集合和B集合的交集
        List<String> bList = new ArrayList<>();
        Collections.addAll(bList, roleIds);

        //复制A集合到C集合，因为在取交集的过程中，会更改集合本身，此时C集合==A集合
        List<String> cList = new ArrayList<>(aList);

        //此时C集合 == （A集合与B集合的交集）为用户依然拥有的角色
        cList.retainAll(bList);

        //更新A集合-C集合的差集为过期的角色，更新状态为"不可用",此时A集合已经全部为过期角色了
        aList.removeAll(cList);

        for (String str : aList) {
            //根据用户ID和角色ID查询，更新状态为过期
            SysUserRole sysUserRole = sysUserRoleMapper.selectByDoubleId(userId, str);
            sysUserRole.setStatus("I");
            BaseDomain.updateLog(sysUserRole);
            sysUserRoleMapper.updateByPrimaryKey(sysUserRole);
        }

        //取B集合-C集合的差集为新增的角色，批量添加
        bList.removeAll(cList);

        //批量插入的时候，如果集合为空，会报错，所以要判断是否为空
        if (!bList.isEmpty()) {
            sysUserRoleMapper.insertMany(userId, bList);
        }
    }

    /**
     * 新增用户，同时添加角色
     *
     * @param sysUser 用户对象
     * @param roleIds 角色ID集合
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 11:13
     */
    @Override
    public void insert(SysUser sysUser, String[] roleIds) {
        sysUserMapper.insert(sysUser);
        putRoleToUser(sysUser.getUserId(), roleIds);
    }

    /**
     * 修改用户，同时修改用户-角色映射
     *
     * @param sysUser 用户对象
     * @param roleIds 角色ID集合
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 11:13
     */
    @Override
    public void update(SysUser sysUser, String[] roleIds) {
        sysUserMapper.updateByPrimaryKey(sysUser);
        updateRoleToUser(sysUser.getUserId(), roleIds);
    }
}
