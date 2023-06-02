package com.zhl.example.processengine.processor;

/**
 * 可以动态选择下一节的节点
 * @author hailang.zhang
 * @since 2023-06-02
 */
public abstract class DynamicProcessor extends AbstractProcessor{

    /**
     * 获取下一个节点的Id
     * @param context 上下文
     * @return 下一个节点的id
     */
    protected abstract String nextNodeId(ProcessContext context);
}