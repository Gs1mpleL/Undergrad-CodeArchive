package com.s1mple.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.s1mple.constant.MessageConstant;
import com.s1mple.entity.Result;
import com.s1mple.pojo.OrderSetting;
import com.s1mple.service.OrderSettingService;
import com.s1mple.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lzh80
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;


    /**
     * 解析上传execl文件
     * @param excelFile 上传的文件
     * @return 是否成功
     */
    @RequestMapping("/upload")
    public Result  upload(@RequestParam("excelFile")MultipartFile excelFile){
        try{
            // 解析文件
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettings = new ArrayList<>();
            // 保存数据库
            for(String[] strings:list){
                String orderDate = strings[0];
                String number = strings[1];
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate),Integer.parseInt(number));
                orderSettings.add(orderSetting);
            }
            orderSettingService.add(orderSettings);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }


    /**
     * 根据月份返回预约情况
     */
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){//参数格式为：2019-03
        try{
            List<Map<String, Integer>> list = orderSettingService.getOrderSettingByMonth(date);
            //获取预约设置数据成功
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            //获取预约设置数据失败
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }


    /**
     * 根据指定日期修改可预约人数
     * @param orderSetting 日期
     * @return 是否成功
     */
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByDate(orderSetting);
            //预约设置成功
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            //预约设置失败
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
