package com.zhaosy.orderservice.service;

import com.zhaosy.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zhaosy
 * @since 2021-04-06
 */
public interface OrderService extends IService<Order> {

    String createOrderId(String courseId, String memberIdByJwtToken);

    Order getByOrderNo(String orderId);
}
