package net.htjs.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.constant.SystemConstant;
import net.htjs.blog.dao.SysRoleMapper;
import net.htjs.blog.dao.SysUserMapper;
import net.htjs.blog.dao.SysUserRoleMapper;
import net.htjs.blog.entity.BaseDomain;
import net.htjs.blog.entity.SysUser;
import net.htjs.blog.entity.SysUserRole;
import net.htjs.blog.service.SysUserService;
import net.htjs.blog.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    private SysRoleMapper sysRoleMapper;

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
     * @param userId     用户主键
     * @param checkedIds 角色ID数组
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 8:33
     */
    @Override
    public void putRoleToUser(String userId, String checkedIds) {

        //盛放没有修改以前的对应记录，用于在修改后删除多余的记录
        Map<String, SysUserRole> map = new HashMap<>(8);

        //获取ID对应的所有权限
        List<SysUserRole> urList = sysUserRoleMapper.selectByUserId(userId);

        //循环处理这些对应记录，逐一放入map中，然后设置该记录为过期，用于标记删除
        for (SysUserRole sysUserRole : urList) {
            //对于该对应记录来说，互斥的ID当成key处理
            map.put(sysUserRole.getRoleId(), sysUserRole);
            //设置所有记录过期
            updateUserRole(sysUserRole, SystemConstant.INVALID);
        }

        //开始处理修改后提交的对应数据，checkedIds为权限集合
        if (null != checkedIds && !"".equals(checkedIds)) {
            String[] ids = checkedIds.split(",");
            for (String id : ids) {
                if (StringUtils.isBlank(id)) {
                    continue;
                }
                //然后看这些ID是否在map中
                SysUserRole sysUserRole = map.get(id);
                if (sysUserRole != null) {
                    //如果在map中，说明在数据库中有记录，把状态改成正常
                    updateUserRole(sysUserRole, SystemConstant.EFFECTIVE);
                } else {
                    //如果不在msp中，说明该对应记录在数据库中没有，要新增
                    sysUserRole = new SysUserRole();
                    BaseDomain.createLog(sysUserRole);
                    sysUserRole.setStatus(SystemConstant.EFFECTIVE);
                    //循环处理的ID
                    sysUserRole.setRoleId(id);
                    //传递过来的Id
                    sysUserRole.setUserId(userId);
                    sysUserRole.setUrId(net.htjs.blog.util.StringUtil.getUUID());
                    sysUserRoleMapper.insert(sysUserRole);
                }
                //同时删除已经处理过的map值
                map.remove(id);
            }
        }
        //当所有值都处理完毕以后，剩下的map值就是：原来有对应关系，修改后没有对应关系，删除之
        for (Map.Entry<String, SysUserRole> entry : map.entrySet()) {
            baseMapper.deleteByPrimaryKey(entry.getValue().getUrId());
        }

    }

    private void updateUserRole(SysUserRole userRole, String status) {
        BaseDomain.updateLog(userRole);
        userRole.setStatus(status);
        sysUserRoleMapper.updateByPrimaryKeySelective(userRole);
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
    public void insert(SysUser sysUser, String roleIds) {
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

    /**
     * 禁用用户
     *
     * @param userId
     * @return boolean
     * @author dingdongliang
     * @date 2018/9/7 9:39
     */
    @Override
    public boolean delUser(String userId) {
        //删除用户角色映射
        List<SysUserRole> urList = sysUserRoleMapper.selectByUserId(userId);
        for (SysUserRole sysUserRole : urList) {
            sysUserRoleMapper.deleteByPrimaryKey(sysUserRole.getUrId());
        }

        //删除用户
        SysUser user = baseMapper.selectByPrimaryKey(userId);
        user.setStatus(SystemConstant.INVALID);
        return baseMapper.updateByPrimaryKey(user) > 0;

    }


    @Override
    public boolean persistenceUser(SysUser sysUser) {

        if (StringUtils.isBlank(sysUser.getUserId())) {
            sysUser.setUserPwd(net.htjs.blog.util.StringUtil.encryptPassword(SystemConstant.DEFAULT_CREDENTIAL, sysUser.getAccount()));

            sysUser.setUserId(StringUtil.getUUID());
            BaseDomain.createLog(sysUser);

            //查询默认权限
            List<String> sysRoleList = sysRoleMapper.selectDefault();

            insert(sysUser, StringUtils.join(sysRoleList, ","));

        } else {
            BaseDomain.updateLog(sysUser);
            updateByPrimaryKeySelective(sysUser);
        }
        return true;
    }

}
