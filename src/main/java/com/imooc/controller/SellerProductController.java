package com.imooc.controller;

import com.imooc.DTO.OrderDTO;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.ProductForm;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductService;
import com.imooc.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 商品列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map){

        PageRequest pageRequest = new PageRequest(page-1, size);

        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        map.put("productInfoPage",productInfoPage);
        return new ModelAndView("product/list",map);
    }

    /**
     * 商品上架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onState(@RequestParam("productId")String productId,
                                Map<String,Object>map ){
        try {
            ProductInfo productInfo = productService.onState(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);
    }

    /**
     * 商品下架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView offState(@RequestParam("productId")String productId,
                                Map<String,Object>map ){
        try {
            ProductInfo productInfo = productService.offState(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);
    }


    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false)String productId,
                              Map<String,Object>map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo",productInfo);
        }

        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("/product/index",map);
    }

    /**
     * 保存
     * @param productForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("/common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            //不为空则为修改
            if(!StringUtils.isEmpty(productForm.getProductId())){
                productInfo = productService.findOne(productForm.getProductId());
            }else {
                productForm.setProductId(KeyUtil.getUniqueKey());
            }
             BeanUtils.copyProperties(productForm,productInfo);
            productService.save(productInfo);
        }catch(SellException e) {
            map.put("msg",e.getMessage());
            map.put("url", "/sell/seller/product/index");
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);
    }

}
