package com.zhl.example.processengine.wrapper.instance;

import com.zhl.example.processengine.processor.Processor;

/**
 * 流程节点实例化器
 * @author hailang.zhang
 * @since 2023-08-17
 */
public interface ProcessInstanceCreator {

    /**
     * 创建实例
     * @param className --类名称
     * @param name --节点名称
     * @return 实例化对象
     */
    Processor newInstance(String className, String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
}
