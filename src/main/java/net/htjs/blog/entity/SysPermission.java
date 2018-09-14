package net.htjs.blog.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * blog/net.htjs.blog.entity
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/13 17:35
 */
@Getter
@Setter
public class SysPermission extends BaseDomain {
    private String pmsnId;
    /**
     * 菜单或者按钮名称
     */
    private String menuName;

    /**
     * 菜单或者按钮代码，用于控制权限，比如user
     */
    private String menuCode;

    /**
     * 父级菜单Id
     */
    private String prntId;

    /**
     * 父级菜单名称
     */
    private String prntName;

    /**
     * 权限ID，用于配合menuCode，控制权限，比如add，和menu结合后就是user:add
     */
    private String pmsnCode;

    /**
     * 小图标
     */
    private String pmsnIcon;

    /**
     * 类型
     */
    private String pmsnType = "menu";

    /**
     * 权限状态，E是可用，I为不可用
     */
    private String status = "E";

    /**
     * 权限对应的映射路径
     */
    private String pmsnUrl;

    /**
     * 权限描述
     */
    private String pmsnDesc;

    /**
     * 是否默认权限，Y为默认，N为非默认
     */
    private String required = "N";

    /**
     * 用于生成TreeView
     */
    private List<SysPermission> nodes;

}