package com.xy.service.impl;

import com.github.pagehelper.PageHelper;
import com.xy.dao.OrdersDao;
import com.xy.domain.Orders;
import com.xy.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersDao ordersDao;

    /**
     * 查询所有订单
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Orders> findAll(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.clearPage();     //手动清理ThreadLocal存储的页参
        return  ordersDao.findAll();
    }

    /**
     * 根据ID查询订单
     *
     * @param ordersId
     * @return
     */
    @Override
    public Orders findById(String ordersId) {
        return ordersDao.findById(ordersId);
    }
}
