package com.s1mple.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.s1mple.constant.MessageConstant;
import com.s1mple.dao.MemberDao;
import com.s1mple.dao.OrderDao;
import com.s1mple.dao.OrderSettingDao;
import com.s1mple.entity.Result;
import com.s1mple.pojo.Member;
import com.s1mple.pojo.Order;
import com.s1mple.pojo.OrderSetting;
import com.s1mple.service.OrderService;
import com.s1mple.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.handler.MessageContext;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 提交预约Service实现
 * @author wanfeng
 * @create 2022/2/24 20:03
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    /**
     * 提交的预约申请处理
     * @param map 前端给一堆数据
     * @return Res
     */
    @Override
    public Result order(Map map) throws Exception {
        // 1.检查预约日期是否有预约设置
        String orderDate = (String)map.get("orderDate");
        OrderSetting byOrderDate = orderSettingDao.findByOrderDate(DateUtils.parseString2Date(orderDate));
        // 为空 说明今天没设置预约
        if(byOrderDate == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        // 检查预约人数是否已满
        if(byOrderDate.getNumber() <= byOrderDate.getReservations()){
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        // 判断是否重复预约
        String email = (String) map.get("email");
        Member member = memberDao.findByTelephone(email);
        if(member!=null){
            Integer id = member.getId();
            Date orderDate1 = DateUtils.parseString2Date(orderDate);
            String setmealId = (String)map.get("setmealId");
            Order order = new Order(id, orderDate1, Integer.parseInt(setmealId));
            // 查询order是否存在
            List<Order> byCondition = orderDao.findByCondition(order);
            if(byCondition!=null && byCondition.size()>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else { // 还不是会员
            member = new Member();
            member.setPhoneNumber((String) map.get("email"));
            member.setName((String) map.get("name"));
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }


        // 预约成功，更新预约人数
        Order order = new Order();
        order.setMemberId(order.getId());
        order.setOrderDate(DateUtils.parseString2Date(orderDate));
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
        orderDao.add(order);

        // 预约人数加1
        byOrderDate.setReservations(byOrderDate.getReservations()+1);
        // 更新预约人数
        orderSettingDao.editReservationsByOrderDate(byOrderDate);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    /**
     * 查询预约信息
     * @param id 根据id查找
     * @return map
     */
    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if(map!=null){
            // 处理日期格式"
            Date orderDate = (Date)map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }
}
