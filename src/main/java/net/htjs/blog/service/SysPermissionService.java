package net.htjs.blog.service;

import net.htjs.blog.entity.SysPermission;
import net.htjs.blog.entity.SysUser;

import java.util.List;

/**
 * blog/net.htjs.blog.service
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/14 11:03
 */
public interface SysPermissionService extends BaseService<SysPermission> {
    /**
     * 根据用户ID查询其所拥有的所有权限
     *
     * @param userId
     * @return java.util.List<net.htjs.blog.entity.SysPermission>
     * @author dingdongliang
     * @date 2018/4/18 15:44
     */
    List<SysPermission> getUserPmsn(String userId);

    /**
     * 查询当前登录用户的权限等信息
     *
     * @param sysUser 当前用户
     * @return java.util.List<net.htjs.blog.entity.SysPermission>
     * @author dingdongliang
     * @date 2018/4/26 17:11
     */
    List<SysPermission> getCurrentPmsn(SysUser sysUser);

    /**
     * 删除权限，需要同时删除角色-权限对应表中的该权限记录
     *
     * @param pmsnId 权限ID
     * @return void
     * @author dingdongliang
     * @date 2018/4/25 16:24
     */
    void delete(String pmsnId);

    /**
     * 查询当前用户的菜单信息，不包含按钮
     *
     * @param userId 当前登录用户的ID
     * @return java.util.List<net.htjs.blog.entity.SysPermission>
     * @author dingdongliang
     * @date 2018/8/30 8:48
     */
    List<SysPermission> getUserMenu(String userId);

    /**
     * 获取所有菜单信息，不包括按钮
     * @param
     * @return java.util.List<net.htjs.blog.entity.SysPermission>
     * @author dingdongliang
     * @date 2018/8/30 14:40
     */
    List<SysPermission> selectAllMenu();
}

