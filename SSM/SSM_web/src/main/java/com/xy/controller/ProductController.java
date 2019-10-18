package com.xy.controller;

import com.xy.service.ProductService;
import com.xy.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
/**
 * 商品控制器
 */
public class ProductController {
    @Autowired
    private ProductService productService;


    @RequestMapping("/findAll.do")
    @Secured({"ROLE_ADMIN"})        //方法权限控制
    /**
     * 查询所有商品信息
     * @return
     * @throws Exception
     */
    public ModelAndView findAll() throws Exception {
        ModelAndView modelAndView=new ModelAndView();
        List<Product> products=productService.findAll();
        modelAndView.addObject("productList",products);
        modelAndView.setViewName("product-list");
        return modelAndView;
    }

    @RequestMapping("/save.do")
    @PreAuthorize("hashRole('ROLE_ADMIN')")     //方法权限控制
    /**
     * 商品添加
     * @param product
     */
    public String save(Product product){
        productService.save(product);
        //添加完成后要重新回到订单页面
        return "redirect:findAll.do";
    }
}
