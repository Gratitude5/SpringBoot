package com.imooc.service;

import com.imooc.DTO.CartDTO;
import com.imooc.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String ProductId);
    /**
     * 查询所有上架商品
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable page);

    ProductInfo save(ProductInfo productInfo);

    //加库存

    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //商品上架
    ProductInfo onState(String productId);

    //下架
    ProductInfo offState(String productId);


}
