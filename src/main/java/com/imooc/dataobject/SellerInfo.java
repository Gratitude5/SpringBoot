package com.imooc.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SellerInfo {
    /**id */
    @Id
    private  String sellerId;

    /**id */
    private  String username;

    /**id */
    private  String password;

    /**id */
    private  String openid;
}
