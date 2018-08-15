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

    private String userName;

    private Date registerTime;

    private String account;

    private String userPwd;

    private String phone;

    private String status = "E";

    private Set<String> menuList;

    private Set<String> pmsnList;
}