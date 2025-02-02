package com.s1mple.dao;

import com.github.pagehelper.Page;
import com.s1mple.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

/**
 * @author lzh80
 */
public interface CheckGroupDao {
    /**
     * 添加检查组
     */
    public void add(CheckGroup checkGroup);

    /**
     * 设置检查项和检查组的关联
     */
    public void setCheckGroupAndCheckItem(Map<String,Integer> map);


    /**
     * 页面查询
     */
    public Page<CheckGroup> selectByCondition(String queryString);

    /**
     * 删除检查组
     * @param id id
     */
    void deleteById(Integer id);

    /**
     * 删除关联
     */
    void deleteCon(Integer id);


    /**
     * 获取关联id
     */
    List<Integer> getConId(Integer id);


    /**
     * 更新基本信息
     */
    public void update(CheckGroup checkGroup);

    /**
     * 更新关联信息
     */
    void updateConId(CheckGroup checkGroup, Integer[] checkItemIds);


    List<CheckGroup> findAll();
}
