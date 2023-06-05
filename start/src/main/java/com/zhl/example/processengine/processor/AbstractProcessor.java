package com.zhl.example.processengine.processor;

/**
 * @author hailang.zhang
 * @since 2023-06-02
 */
public abstract class AbstractProcessor implements Processor {

    private String name;

    @Override
    public void process(ProcessContext context) {
        beforeProcess(context);
        processInternal(context);
        afterProcess(context);
    }

    /**
     * 流程的核心逻辑
     * @param context 上下文
     */
    protected abstract void processInternal(ProcessContext context);

    /**
     * 流程前操作
     * @param context 上下文
     */
    protected void beforeProcess(ProcessContext context) {

    }

    /**
     * 流程后操作
     * @param context
     */
    protected void afterProcess(ProcessContext context) {

    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void caughtException(ProcessContext context, Throwable throwable) {

    }
}