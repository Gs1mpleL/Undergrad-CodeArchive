package com.s1mple.service;

import com.s1mple.entity.PageResult;
import com.s1mple.entity.QueryPageBean;
import com.s1mple.pojo.CheckItem;

import java.util.List;

/**
 *  服务接口
 * @author lzh80
 */
public interface CheckItemService {

    /**
     * 添加体检项目
     * @param checkItem 体检项目
     */
    public void add(CheckItem checkItem);

    /**
     * 分页查询
     * @param queryPageBean 查询条件
     * @return 页面结果
     */
    public PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 通过id删除数据
     * @param id id
     */
    public void deleteById(Integer id);

    /**
     * 更新数据
     */
    public void updateById(CheckItem checkItem);

    /**
     * 查询所有
     */
    public List<CheckItem> findAll();
}
