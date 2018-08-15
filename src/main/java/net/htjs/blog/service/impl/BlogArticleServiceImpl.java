package net.htjs.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.entity.BlogArticle;
import net.htjs.blog.service.BlogArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
