package com.sxt.system.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * create by zkc 2020.09/07
 * 当实现了ApplicationContextAware这个接口之后，Ioc容器初始化的时候会回调setApplicationContext方法，把Ioc容器对象传到里面来
 *；主要是取出IOC容器
 */
@Component
public class AppUtils implements ApplicationContextAware{

    private static ApplicationContext context;

    public static ApplicationContext getContext(){
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
