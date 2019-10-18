package com.xy.dao;

import com.xy.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 游客DAO
 */
public interface TravellerDao {

    //先从订单表里查询对应的游客ID，再从游客表里根据ID查询游客
    @Select("select *from traveller where id=(select travellerId from order_traveller where orderId=#{ordersId})")
    /**
     * 根据订单ID查询游客
     * @param ordersId
     * @return
     */
    public List<Traveller> findByOrdersId(String ordersId);
}
