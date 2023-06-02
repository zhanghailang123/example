package com.zhl.example.processengine.processor;

/**
 * 可回滚的流程
 * @author hailang.zhang
 * @since 2023-06-02
 */
public abstract class RollbackProcessor extends AbstractProcessor{

    /**
     * 回滚操作
     * @param context 上下文
     */
    protected abstract void rollback(ProcessContext context);
}