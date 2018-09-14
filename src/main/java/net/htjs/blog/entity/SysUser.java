package net.htjs.blog.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

/**
 * blog/net.htjs.blog.entity
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/13 17:35
 */
@Getter
@Setter
public class SysUser extends BaseDomain{
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登录帐号
     */
    private String account;

    /**
     * 密码，默认6个1
     */
    private String userPwd;


    private String phone;

    /**
     * 是否有效，E有效，I无效
     */
    private String status = "E";

    private Set<String> menuList;

    private Set<String> pmsnList;
}