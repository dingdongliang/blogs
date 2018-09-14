package net.htjs.blog.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.dao.BlogArticleMapper;
import net.htjs.blog.entity.BaseDomain;
import net.htjs.blog.entity.BlogArticle;
import net.htjs.blog.service.BlogArticleService;
import net.htjs.blog.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * blog/net.htjs.blog.service.impl
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/15 10:55
 */
@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
public class BlogArticleServiceImpl extends BaseServiceImpl<BlogArticle> implements BlogArticleService {
    @Resource
    private BlogArticleMapper blogArticleMapper;

    /**
     * 新建或者修改文章
     *
     * @param blogArticle
     * @return boolean
     * @author dingdongliang
     * @date 2018/9/14 15:49
     */
    @Override
    public boolean saveArticle(BlogArticle blogArticle) {

        if (StringUtils.isBlank(blogArticle.getArticleId())) {
            blogArticle.setArticleId(StringUtil.getUUID());
            BaseDomain.createLog(blogArticle);
            blogArticle.setArticleViews(1);
            blogArticleMapper.insert(blogArticle);
        } else {
            BlogArticle oldArticle = blogArticleMapper.selectByPrimaryKey(blogArticle.getArticleId());
            oldArticle.setArticleTitle(blogArticle.getArticleTitle());
            oldArticle.setArticleSummary(blogArticle.getArticleSummary());
            oldArticle.setArticleInfo(blogArticle.getArticleInfo());
            oldArticle.setSortId(blogArticle.getSortId());
            BaseDomain.updateLog(oldArticle);
            blogArticleMapper.updateByPrimaryKey(oldArticle);
        }
        return true;
    }
}
