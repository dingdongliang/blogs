package net.htjs.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.htjs.blog.dao.BaseMapper;
import net.htjs.blog.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * blog/net.htjs.blog.service.impl
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/13 16:39
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    protected BaseMapper<T> baseMapper;

    /**
     * 根据id查询实体
     *
     * @param id 主键
     * @return T
     * @author dingdongliang
     * @date 2018/4/18 9:16
     */
    @Override
    public T selectByPrimaryKey(String id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    /**
     * 全部分页
     *
     * @param page 页数
     * @param rows 行数
     * @return com.github.pagehelper.PageInfo<T>
     * @author dingdongliang
     * @date 2018/4/18 9:16
     */
    @Override
    public PageInfo<T> selectPageByAll(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<T> list = baseMapper.selectAll();
        return new PageInfo<>(list);
    }

    /**
     * 查询所有
     *
     * @return java.util.List<T>
     * @author dingdongliang
     * @date 2018/4/18 9:16
     */
    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    /**
     * 插入
     *
     * @param t 实体类
     * @return int
     * @author dingdongliang
     * @date 2018/4/18 9:17
     */
    @Override
    public int insert(T t) {
        return baseMapper.insert(t);
    }

    /**
     * 新增非空字段
     *
     * @param t 实体类
     * @return int
     * @author dingdongliang
     * @date 2018/4/18 9:17
     */
    @Override
    public int insertSelective(T t) {
        return baseMapper.insertSelective(t);
    }

    /**
     * 根据主键更新
     *
     * @param t 实体类
     * @return int
     * @author dingdongliang
     * @date 2018/4/18 9:17
     */
    @Override
    public int updateByPrimaryKey(T t) {
        return baseMapper.updateByPrimaryKey(t);
    }

    /**
     * 根据主键更新非空字段
     *
     * @param t 实体类
     * @return int
     * @author dingdongliang
     * @date 2018/4/18 9:17
     */
    @Override
    public int updateByPrimaryKeySelective(T t) {
        return baseMapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return int
     * @author dingdongliang
     * @date 2018/4/18 9:17
     */
    @Override
    public int deleteByPrimaryKey(String id) {
        return baseMapper.deleteByPrimaryKey(id);
    }
}
