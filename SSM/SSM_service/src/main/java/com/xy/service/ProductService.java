package com.xy.service;

import com.xy.dao.ProductDao;
import com.xy.domain.Product;

import java.util.List;

/**
 * 业务层接口
 */
public interface ProductService {

    /**
     * 查询所有商品接口
     * @return
     * @throws Exception
     */
    public List<Product> findAll() throws Exception;

    /**
     * 保存商品
     * @param product
     */
    public void save(Product product);
}
