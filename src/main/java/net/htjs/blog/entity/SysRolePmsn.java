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

    private String status = "E";
}