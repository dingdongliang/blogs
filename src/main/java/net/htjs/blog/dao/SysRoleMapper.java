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

    List<String> selectDefault();
}