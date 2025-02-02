package com.s1mple.service;

import com.s1mple.entity.PageResult;
import com.s1mple.pojo.Setmeal;

import java.util.List;

/**
 * 体检套餐服务接口
 */
public interface SetmealService {
    /**
     * 添加套餐
     * @param setmeal 套餐基本信息
     * @param checkgroupIds 关联检查项
     */
    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 分页查询
     * @return 页面信息
     */
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 查询所以套餐
     * @return 套餐list
     */
    public List<Setmeal> findAll();

    /**
     * 根据id查询
     * @return Setmeal
     */
    public Setmeal findById(int id);
}