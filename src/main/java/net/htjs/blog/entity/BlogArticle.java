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

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 分类Id
     */
    private String sortId;

    /**
     * 浏览量
     */
    private Integer articleViews;

    /**
     * 文章当前状态,E:审批通过,W:等待审批,I:下架
     */
    private String status = "W";

    /**
     * 文章内容
     */
    private String articleInfo;

    /**
     * 文章概要
     */
    private String articleSummary;
}