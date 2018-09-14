package net.htjs.blog.service;

import net.htjs.blog.entity.BlogArticle;

/**
 * blog/net.htjs.blog.service
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/15 10:55
 */
public interface BlogArticleService extends BaseService<BlogArticle> {
    /**
     * 新建或者修改文章
     *
     * @param blogArticle
     * @return boolean
     * @author dingdongliang
     * @date 2018/9/14 15:49
     */
    boolean saveArticle(BlogArticle blogArticle);
}
