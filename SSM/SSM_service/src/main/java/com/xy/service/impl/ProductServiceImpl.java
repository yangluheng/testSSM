package com.xy.service.impl;

import com.xy.dao.ProductDao;
import com.xy.domain.Product;
import com.xy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务层接口实现类
 */
@Service
@Transactional
public class ProductServiceImpl  implements ProductService {
    @Autowired
    private ProductDao productDao;
    /**
     * 查询所有商品接口
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Product> findAll() throws Exception {
        return productDao.findAll();
    }

    /**
     * 保存商品
     *
     * @param product
     */
    @Override
    public void save(Product product) {
        productDao.save(product);
    }
}
