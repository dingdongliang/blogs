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
public class BlogSort extends BaseDomain {
    private String sortId;

    /**
     * 文章分类名称
     */
    private String sortName;

    public BlogSort(String sortId, String sortName) {
        this.sortId = sortId;
        this.sortName = sortName;
    }
}