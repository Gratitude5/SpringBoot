package com.imooc.utils;

import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一的主键
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random = new Random();

        System.currentTimeMillis();
        Integer number =  random.nextInt(900000) + 100000;

        return System.currentTimeMillis()+String.valueOf(number);
    }
}
