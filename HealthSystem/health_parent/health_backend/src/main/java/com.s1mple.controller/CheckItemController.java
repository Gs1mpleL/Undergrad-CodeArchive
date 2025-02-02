package com.s1mple.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.s1mple.constant.MessageConstant;
import com.s1mple.entity.PageResult;
import com.s1mple.entity.QueryPageBean;
import com.s1mple.entity.Result;
import com.s1mple.pojo.CheckItem;
import com.s1mple.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查项controller
 * @author lzh80
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    /**
     *  注解是去服务注册中心查找服务
     */
    @Reference
    private CheckItemService checkItemService;

    /**
     * 添加体检项目
     * @param checkItem 体检项目实体类
     * @return 成功失败
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try{
            checkItemService.add(checkItem);
        }catch (Exception e){
            // 服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }


    /**
     * 检查项分页查询
     */
    @RequestMapping("/pageQuery")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkItemService.findPage(queryPageBean);
    }


    /**
     * 通过id删除检查项
     */
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('MENU_ADD')")  // 检查是否有权限
    public Result deleteById(Integer id){
        try{
            checkItemService.deleteById(id);
        }catch (RuntimeException e){
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_CHECKITEM_FAIL);
        } catch (Exception e){
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    /**
     * 通过实体更新检查项
     */
    @RequestMapping("/update")
    public Result updateById(@RequestBody CheckItem checkItem){
        try{
            checkItemService.updateById(checkItem);
        } catch (Exception e){
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }


    /**
     * 查询所有检查项
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<CheckItem> checkItemList = checkItemService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
