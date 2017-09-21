package com.zhou.rpc.service.impl;

import com.zhou.rpc.api.SystemService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author eli
 * @date 2017/9/20 17:56
 */
@Transactional
public class SystemServiceImpl implements SystemService{
    public void test(){
        System.out.println("SystemServiceImpl.test()执行!");
    }
}
