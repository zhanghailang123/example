package com.zhl.example.processengine.node;

import com.zhl.example.processengine.enums.InvokeMethod;
import com.zhl.example.processengine.processor.Processor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程节点的通用定义
 * @author hailang.zhang
 * @since 2023-06-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessorNode {

    private String name;

    /**
     * 执行的动作
     */
    private Processor processor;

    private Map<String, ProcessorNode> nextNodes = new HashMap<>();

    private InvokeMethod invokeMethod = InvokeMethod.SYNC;

    /**
     * 是否已经存在同步的下一个节点
     */
    private boolean hasSyncNextNode = false;

    public void addNextNode(ProcessorNode node) {
        if (node.getName().equals(name)) {
            throw new IllegalStateException("Duplicate node id : " + name);
        }
        if (nextNodes.containsKey(node.getName())) {

        }
        boolean isSync = InvokeMethod.SYNC.equals(node.invokeMethod);

        //TODO zhl 待完成动态节点
        boolean isDynamic = false;
        if (!isDynamic && hasSyncNextNode && isSync) {
            throw new IllegalArgumentException("每个节点只能有一个同步调用的后继节点");
        }
        if (isSync) {
            hasSyncNextNode = true;
        }
        nextNodes.put(node.getName(), node);
    }

}