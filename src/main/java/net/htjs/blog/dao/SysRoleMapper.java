package net.htjs.blog.dao;

import net.htjs.blog.entity.SysRole;

import java.util.List;

/**
 * blog/net.htjs.blog.dao
 *
 * @Description: 角色表
 * @Author: dingdongliang
 * @Date: 2018/8/13 17:29
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 获取默认的角色ID
     * @param
     * @return java.util.List<java.lang.String>
     * @author dingdongliang
     * @date 2018/9/13 9:46
     */
    List<String> selectDefault();
}