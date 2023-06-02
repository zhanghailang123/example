package com.zhl.example.processengine.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author hailang.zhang
 * @since 2023-06-02
 */
public class ProcessContext {

    private final Map<String, Object> params = new HashMap<>();

//    private final

    private final Stack<RollbackProcessor> rollbackProcessors = new Stack<>();
}