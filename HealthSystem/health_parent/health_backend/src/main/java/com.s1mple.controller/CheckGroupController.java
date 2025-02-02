package com.s1mple.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.s1mple.constant.MessageConstant;
import com.s1mple.entity.PageResult;
import com.s1mple.entity.QueryPageBean;
import com.s1mple.entity.Result;
import com.s1mple.pojo.CheckGroup;
import com.s1mple.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lzh80
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {


    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组
     * @param checkGroup 检查组基本信息
     * @param checkItemIds 检查组包括的检查项的id集合
     * @return 结果
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkItemIds){
        try{
            checkGroupService.add(checkGroup,checkItemIds);
        }catch(Exception e){
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页展示
     * @param queryPageBean 分页需要的内容
     * @return 分页信息和数据
     */
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.findPage(queryPageBean);
    }



    /**
     * 通过id删除检查组
     */
    @RequestMapping("/delete")
    public Result deleteById(Integer id){
        try{
            checkGroupService.deleteById(id);
        } catch (Exception e){
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }


    /**
     * 获取检查组关联的检查项
     * @param id 检查组id
     * @return 关联检查项的集合
     */
    @RequestMapping("/getConId")
    public Result getConId(Integer id) {
        List<Integer> list;
        try {
            list = checkGroupService.getConId(id);
        } catch (Exception e) {
            return new Result(false,MessageConstant.CHECK_CHECKGROUP_CHECKITEM_CONN_FAIL);
        }
        return new Result(true, MessageConstant.CHECK_CHECKGROUP_CHECKITEM_CONN_SUCCESS, list);
    }


    /**
     * 修改检查组
     * @param checkGroup 基本信息
     * @param checkItemIds 关联检查项
     * @return 是否成功
     */
    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkItemIds){
        try{
            checkGroupService.update(checkGroup,checkItemIds);
        }catch(Exception e){
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }


    /**
     * 查询所以检查组
     * @return 所以检查组
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        if(checkGroupList != null && checkGroupList.size() > 0){
            Result result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS);
            result.setData(checkGroupList);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

}
