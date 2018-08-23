package net.htjs.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.entity.BlogSort;
import net.htjs.blog.service.BlogSortService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * blog/net.htjs.blog.service.impl
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/15 18:49
 */
@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
public class BlogSortServiceImpl extends BaseServiceImpl<BlogSort> implements BlogSortService {
}
