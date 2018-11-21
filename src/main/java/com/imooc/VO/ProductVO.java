package com.imooc.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品(包含类目)
 */
@Data
public class ProductVO implements Serializable {


    private static final long serialVersionUID = -4149208126281721217L;
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categorytype;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
