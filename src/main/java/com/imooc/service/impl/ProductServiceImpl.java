package com.imooc.service.impl;

import com.imooc.DTO.CartDTO;
import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.repository.ProductInfoReponsitory;
import com.imooc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoReponsitory reponsitory;
    @Override
    public ProductInfo findOne(String productId) {

        return reponsitory.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return reponsitory.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable page) {
        return reponsitory.findAll(page);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return reponsitory.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {

    }

    @Override
    @Transient
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = reponsitory.findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result <0 ){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);

            reponsitory.save(productInfo);
        }
    }

    @Override
    public ProductInfo onState(String productId) {
        ProductInfo productInfo = reponsitory.findOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum() ==ProductStatusEnum.UP){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR );
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return reponsitory.save(productInfo);
    }

    @Override
    public ProductInfo offState(String productId) {
        ProductInfo productInfo = reponsitory.findOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum() ==ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR );
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return reponsitory.save(productInfo);
    }
}
