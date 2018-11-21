package com.imooc.service;

import com.imooc.DTO.OrderDTO;

public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
