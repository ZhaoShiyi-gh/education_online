package com.zhaosy.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaosy.commenUtils.JwtUtils;
import com.zhaosy.commenUtils.R;
import com.zhaosy.orderservice.entity.Order;
import com.zhaosy.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zhaosy
 * @since 2021-04-06
 */
@RestController
@RequestMapping("/orderservice/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        String orderId = orderService.createOrderId(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    @GetMapping("/getOrderInfoById/{orderId}")
    public R getOrderInfoById(@PathVariable String orderId){
        Order order = orderService.getByOrderNo(orderId);
        return R.ok().data("order", order);
    }

    @GetMapping("/isBuy/{courseId}/{memberId}")
    public boolean isBuy(@PathVariable String courseId, @PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        Order one = orderService.getOne(wrapper);
        if (one == null){
            return false;
        }else {
            return true;
        }
    }
}

