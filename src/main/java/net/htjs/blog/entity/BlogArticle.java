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
public class BlogArticle extends BaseDomain {
    private String articleId;

    private String articleTitle;

    private String sortId;

    private Integer articleViews;

    private String status = "E";

    private String articleInfo;

    private String articleSummary;
}