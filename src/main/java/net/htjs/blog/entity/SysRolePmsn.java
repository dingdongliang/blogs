package net.htjs.blog.entity;

import lombok.Getter;
import lombok.Setter;
/**
 * blog/net.htjs.blog.entity
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/13 17:35
 */
@Getter
@Setter
public class SysRolePmsn extends BaseDomain {
    private String rpId;

    private String roleId;

    private String pmsnId;

    /**
     * 角色权限映射可用性，E为可用，I为不可用
     */
    private String status = "E";
}