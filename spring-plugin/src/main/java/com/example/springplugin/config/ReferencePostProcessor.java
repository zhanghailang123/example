package com.example.springplugin.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author hailang.zhang
 * @since 2023-07-14
 */
public class ReferencePostProcessor implements FactoryBean, InitializingBean {

    //find some deep think and just do it and test
    //for example Zrpc 的 reference bean 再 head back  to
    //shenyu

    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}