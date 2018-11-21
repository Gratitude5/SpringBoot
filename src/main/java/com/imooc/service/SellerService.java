package com.imooc.service;

import com.imooc.dataobject.SellerInfo;

public interface SellerService {

    SellerInfo findSellInfoByOpenid(String openid);

}
