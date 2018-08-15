package net.htjs.blog.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * blog/net.htjs.blog.entity
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/13 17:35
 */
@Getter
@Setter
public class SysRole extends BaseDomain {
    private String roleId;

    private String roleName;

    private String roleDesc;

    private String status = "E";

    private String isDefault = "N";

}