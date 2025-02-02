package com.s1mple.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.s1mple.dao.CheckItemDao;
import com.s1mple.entity.PageResult;
import com.s1mple.entity.QueryPageBean;
import com.s1mple.pojo.CheckItem;
import com.s1mple.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author lzh80
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;


    /**
     * 添加体检项目
     * @param checkItem 体检项目
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
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
        Page<CheckItem> page = checkItemDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 通过id删除数据
     *
     * @param id id
     */
    @Override
    public void deleteById(Integer id) {
        long count = checkItemDao.findCountByCheckItemId(id);
        if(count > 0){
            // 已经被关联就抛出异常
            throw new RuntimeException();
        }else{
            checkItemDao.deleteById(id);
        }
    }

    /**
     * 更新数据
     *
     * @param checkItem
     */
    @Override
    public void updateById(CheckItem checkItem) {
        checkItemDao.updateById(checkItem);
    }

    /**
     * 查询所有
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
