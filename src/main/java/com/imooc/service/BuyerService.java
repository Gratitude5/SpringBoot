package com.imooc.service;

import com.imooc.DTO.OrderDTO;

/**
 * 判断买家
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderId);

    //取消订单
    OrderDTO cancleOrder(String openid,String orderId);
}
