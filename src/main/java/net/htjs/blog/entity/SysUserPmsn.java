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
public class SysUserPmsn extends BaseDomain {
    private String upmId;

    private String userId;

    private String pmsnId;

    private String status = "E";


}