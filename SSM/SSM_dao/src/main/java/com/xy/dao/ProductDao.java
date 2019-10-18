package com.xy.dao;

import com.xy.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品的dao层
 */
public interface ProductDao {
    @Select("select *from product where id=#{id}")
    /**
     * 根据ID查询商品
     * @param id
     * @return
     */
    public Product findById(String id);

    /**
     * 查询所有的商品信息
     * @return
     * @throws Exception
     */
    @Select("select *from product")
    public List<Product> findAll() throws  Exception;

    /**
     * 添加商品
     * @param product
     */
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    public void save(Product product);
}
