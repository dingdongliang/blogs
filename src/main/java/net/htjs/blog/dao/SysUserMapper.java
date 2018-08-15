package net.htjs.blog.dao;

import net.htjs.blog.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * blog/net.htjs.blog.dao
 *
 * @Description: 用户表
 * @Author: dingdongliang
 * @Date: 2018/8/13 17:29
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户名和密码查询对应的用户，用于登录校验
     *
     * @param account 用户登录账号，和userName不一样
     * @return net.htjs.blog.entity.SysUser
     * @author dingdongliang
     * @date 2018/4/12 17:43
     */
    SysUser userCertified(@Param("account") String account);
}