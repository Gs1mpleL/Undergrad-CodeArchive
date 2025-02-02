package com.s1mple.service;

import com.s1mple.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author lzh80
 */
public interface OrderSettingService {
    /**
     * 添加预约
     * @param list 预约对象实体列表
     */
    public void add(List<OrderSetting> list);

    /**
     * 获取预约信息
     * @param date 日期
     * @return 预约的信息
     */
    public List<Map<String, Integer>> getOrderSettingByMonth(String date);

    /**
     * 根据日期编辑预约
     * @param orderSetting 日期实体
     */
    public void editNumberByDate(OrderSetting orderSetting);
}
