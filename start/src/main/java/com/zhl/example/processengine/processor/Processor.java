package com.zhl.example.processengine.processor;

/**
 * 准确点说这个类就是要执行的动作
 * @author hailang.zhang
 * @since 2023-06-02
 */
public interface Processor {

    void process(ProcessContext context);

    void setName(String name);

    String getName();

    /**
     * 触发异常了
     * @param context 上下文
     * @param throwable 异常
     */
    void caughtException(ProcessContext context, Throwable throwable);
}
