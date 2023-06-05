package com.zhl.example.processengine.processor;

import com.zhl.example.processengine.enums.InvokeMethod;
import com.zhl.example.processengine.node.ProcessorDefinition;
import com.zhl.example.processengine.node.ProcessorNode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author hailang.zhang
 * @since 2023-06-02
 */
@Slf4j
public class ProcessContext {

    private final Map<String, Object> params = new HashMap<>();

    private final ProcessorDefinition processorDefinition;

    private final Stack<RollbackProcessor> rollbackProcessors = new Stack<>();

    public ProcessContext(ProcessorDefinition processorDefinition) {
        this.processorDefinition = processorDefinition;
    }

    public void set(String key, Object value) {
        params.put(key, value);
    }

    public <T> T get(String key) {
        return (T) params.get(key);
    }

    private void process(ProcessorNode node) {
        if (node  == null) {
            return;
        }

        Processor processor = node.getProcessor();

        try {
            if (processor instanceof RollbackProcessor) {
                rollbackProcessors.push((RollbackProcessor) processor);
            }
            processor.process(this);
        } catch (Exception e) {
            //回滚前面 所有可回滚节点，按照所有节点的顺序倒序回滚
            RollbackProcessor rollbackProcessor;
            while (!rollbackProcessors.empty()) {
                rollbackProcessor = rollbackProcessors.pop();
                try {
                    rollbackProcessor.rollback(this);
                } catch (Exception e1) {
                    //TODO 此处回滚异常了该怎么处理呢
                }
            }
            processor.caughtException(this, e);
            throw e;
        }

        ProcessorNode nextNode = null;
        Map<String, ProcessorNode> nextNodes = node.getNextNodes();
        if (processor instanceof DynamicProcessor) {
            DynamicProcessor dynamicProcessor = (DynamicProcessor) processor;
            String nextNodeId = dynamicProcessor.nextNodeId(this);
            if (!nextNodes.containsKey(nextNodeId)) {
                throw new IllegalArgumentException("DunamicProcess can not find next node with id  :" + nextNodeId);
            }
            nextNode = nextNodes.get(nextNodeId);
        } else {
            if (!nextNodes.isEmpty()) {
                nextNode = nextNodes.values().stream().findAny().get();
            }
        }

        InvokeMethod invokeMethod = node.getInvokeMethod();
        if (invokeMethod.equals(InvokeMethod.SYNC)) {
            process(nextNode);
        } else {
            ProcessorNode finalNextNode = nextNode;
            ProcessorUtils.executeAsync(() -> process(finalNextNode));
        }
    }
}