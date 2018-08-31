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
public class SysPermission extends BaseDomain {
    private String pmsnId;

    private String menuName;

    private String menuCode;

    private String prntId;

    private String prntName;

    private String pmsnCode;

    private String pmsnIcon;

    private String pmsnType = "menu";

    private String status = "E";

    private String pmsnUrl;

    private String pmsnDesc;

    private String required = "N";

}