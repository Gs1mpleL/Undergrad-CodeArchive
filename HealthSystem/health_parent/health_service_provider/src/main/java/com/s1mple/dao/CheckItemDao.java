package com.s1mple.dao;

import com.github.pagehelper.Page;
import com.s1mple.pojo.CheckItem;

import java.util.List;

/**
 * @author lzh80
 */
public interface CheckItemDao{
    /**
     * 添加体检项目
     * @param checkItem 添加体检项目
     */
    public void add(CheckItem checkItem);


    /**
     * 按条件查询页面
     * @param queryString 查询条件
     * @return 页面
     */
    public Page<CheckItem> selectByCondition(String queryString);


    /**
     * 删除一条信息
     * @param id id
     */
    public void deleteById(Integer id);


    /**
     * 根据id查管关联表
     */
    public long findCountByCheckItemId(Integer id);


    /**
     * 更新体检项目信息
     */
    public void updateById(CheckItem checkItem);


    /**
     * 查询所有
     */
    public List<CheckItem> findAll();
}
