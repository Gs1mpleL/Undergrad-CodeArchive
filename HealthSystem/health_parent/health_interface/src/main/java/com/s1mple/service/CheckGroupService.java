package com.s1mple.service;

import com.s1mple.entity.PageResult;
import com.s1mple.entity.QueryPageBean;
import com.s1mple.pojo.CheckGroup;

import java.util.List;

/**
 * @author lzh80
 */
public interface CheckGroupService {
    /**
     * 添加检查组
     */

    public void add(CheckGroup checkGroup,Integer[] checkitemIds);

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
     * 获得关联id
     */
    public List<Integer> getConId(Integer id);


    /**
     * 修改信息
     * @param checkGroup 基本信息
     * @param checkItemIds 关联检查项
     */
    void update(CheckGroup checkGroup, Integer[] checkItemIds);



    List<CheckGroup> findAll();
}
