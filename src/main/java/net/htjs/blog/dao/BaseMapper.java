package net.htjs.blog.dao;

import java.util.List;

/**
 * blog/net.htjs.blog.dao
 *
 * @Description: 自定义通用Mapper接口，没有继承tk.mapper接口，因会造成其他问题
 * @Author: dingdongliang
 * @Date: 2018/8/13 17:29
 */
public interface BaseMapper<T> {
    /**
     * 查询所有
     *
     * @return java.util.List<T>
     * @author dingdongliang
     * @date 2018/4/18 9:16
     */
    List<T> selectAll();

    /**
     * 根据id查询实体
     *
     * @param key 一般是主键
     * @return T
     * @author dingdongliang
     * @date 2018/4/18 9:16
     */
    T selectByPrimaryKey(Object key);

    /**
     * 插入
     *
     * @param t 实体类
     * @return int
     * @author dingdongliang
     * @date 2018/4/18 9:17
     */
    int insert(T t);

    /**
     * 新增非空字段
     *
     * @param t 实体类
     * @return int
     * @author dingdongliang
     * @date 2018/4/18 9:17
     */
    int insertSelective(T t);

    /**
     * 根据主键更新
     *
     * @param t 实体类
     * @return int
     * @author dingdongliang
     * @date 2018/4/18 9:17
     */
    int updateByPrimaryKey(T t);

    /**
     * 根据主键更新非空字段
     *
     * @param t 实体类
     * @return int
     * @author dingdongliang
     * @date 2018/4/18 9:17
     */
    int updateByPrimaryKeySelective(T t);

    /**
     * 根据主键删除
     *
     * @param key 一般是主键
     * @return int
     * @author dingdongliang
     * @date 2018/4/18 9:17
     */
    int deleteByPrimaryKey(Object key);
}
