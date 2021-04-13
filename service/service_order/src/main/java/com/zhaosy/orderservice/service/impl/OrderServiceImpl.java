package com.zhaosy.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaosy.commenUtils.CourseInfoForm;
import com.zhaosy.commenUtils.UCenterInfoForm;
import com.zhaosy.orderservice.client.EduClient;
import com.zhaosy.orderservice.client.UCenterClient;
import com.zhaosy.orderservice.entity.Order;
import com.zhaosy.orderservice.mapper.OrderMapper;
import com.zhaosy.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaosy.orderservice.util.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zhaosy
 * @since 2021-04-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    EduClient eduClient;

    @Autowired
    UCenterClient uCenterClient;

    @Override
    public String createOrderId(String courseId, String memberIdByJwtToken) {

        CourseInfoForm courseInfo = eduClient.getCourseInfoDto(courseId);
        UCenterInfoForm memberInfo = uCenterClient.getInfo(memberIdByJwtToken);


        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberInfo.getId());
        order.setMobile(memberInfo.getMobile());
        order.setNickname(memberInfo.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }

    @Override
    public Order getByOrderNo(String orderId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_no", orderId);
        Order order = baseMapper.selectOne(wrapper);
        return order;
    }
}
