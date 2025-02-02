package com.s1mple.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.s1mple.dao.CheckGroupDao;
import com.s1mple.entity.PageResult;
import com.s1mple.entity.QueryPageBean;
import com.s1mple.pojo.CheckGroup;
import com.s1mple.pojo.CheckItem;
import com.s1mple.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzh80
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     *
     * @param checkGroup 表单信息
     * @param checkitemIds 包括的检查项
     */
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 新增检查组
        checkGroupDao.add(checkGroup);
        // 设置对应检查项
        Integer checkGroupId = checkGroup.getId();
        if(checkitemIds!=null && checkitemIds.length>0){
            Map<String,Integer> map = new HashMap<>();
            for(Integer checkitemId:checkitemIds){
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean 查询条件
     * @return 页面结果
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 设置页面助手 通过线程绑定来实现添加数据
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        // 查询后返回helper的一个Page类 内部就有需要的数据
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 通过id删除数据
     *
     * @param id id
     */
    @Override
    public void deleteById(Integer id) {
        checkGroupDao.deleteCon(id);
        checkGroupDao.deleteById(id);
    }

    /**
     * 获得关联id
     * @param id
     */
    @Override
    public List<Integer> getConId(Integer id) {
        return checkGroupDao.getConId(id);
    }

    /**
     * 修改信息
     *
     * @param checkGroup   基本信息
     * @param checkItemIds 关联检查项
     */
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkItemIds) {
        // 更新基本信息
        checkGroupDao.update(checkGroup);
        checkGroupDao.deleteCon(checkGroup.getId());
        // 更新关联信息
        if(checkItemIds!=null && checkItemIds.length>0){
            Map<String,Integer> map = new HashMap<>();
            for(Integer checkitemId:checkItemIds){
                map.put("checkgroupId",checkGroup.getId());
                map.put("checkitemId",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
