package com.zhl.example.processengine.wrapper.instance;

import com.zhl.example.processengine.processor.Processor;

/**
 * @author hailang.zhang
 * @since 2023-08-17
 */
public class SpringBeanInstanceCreator implements ProcessInstanceCreator{

    @Override
    public Processor newInstance(String className, String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(className);
        Object o = SpringContextUtil.getBean(clazz);
        if (!(o instanceof Processor)) {
            throw new IllegalArgumentException("类" + className + "不是Processor实例");
        }

        Processor processor = (Processor) o;
        processor.setName(name);
        return (Processor) o;
    }
}