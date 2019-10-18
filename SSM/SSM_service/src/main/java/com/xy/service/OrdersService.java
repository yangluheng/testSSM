package com.xy.service;

import com.xy.domain.Orders;

import java.util.List;

/**
 * 订单业务层接口
 */
public interface OrdersService {
    /**
     * 查询所有订单
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Orders> findAll(int pageNum,int pageSize);

    /**
     * 根据ID查询订单
     * @param ordersId
     * @return
     */
    public Orders findById(String ordersId);
}
