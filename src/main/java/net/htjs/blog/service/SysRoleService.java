package net.htjs.blog.service;

import net.htjs.blog.entity.SysRole;

/**
 * blog/net.htjs.blog.service
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/14 11:02
 */
public interface SysRoleService extends BaseService<SysRole> {

    /**
     * 给角色赋予权限，一对多
     *
     * @param roleId  角色ID
     * @param pmsnIds 权限ID数组
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 8:33
     */
    void putPmsnToRole(String roleId, String[] pmsnIds);

    /**
     * 更新用户的角色
     *
     * @param roleId  角色ID
     * @param pmsnIds 权限ID数组
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 9:31
     */
    void updatePmsnToRole(String roleId, String[] pmsnIds);

    /**
     * 新增角色，同时分配权限
     *
     * @param sysRole 角色对象
     * @param pmsnIds 权限集合
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 11:13
     */
    void insert(SysRole sysRole, String[] pmsnIds);

    /**
     * 修改角色，同时修改角色-权限映射
     *
     * @param sysRole 角色对象
     * @param pmsnIds 权限集合
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 11:13
     */
    void update(SysRole sysRole, String[] pmsnIds);

    /**
     * 删除角色，在删除角色之前，要先删除该角色对应的权限
     *
     * @param roleId 角色ID
     * @return void
     * @author dingdongliang
     * @date 2018/4/25 16:24
     */
    void delete(String roleId);
}


