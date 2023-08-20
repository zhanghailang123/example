package com.zhl.example.processengine.wrapper.model;

import com.zhl.example.processengine.node.ProcessorDefinition;
import com.zhl.example.processengine.node.ProcessorNode;
import com.zhl.example.processengine.processor.Processor;
import com.zhl.example.processengine.wrapper.instance.ProcessInstanceCreator;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hailang.zhang
 * @since 2023-06-06
 */
@Data
public class ProcessModel {
    private String name;
    private Map<String, ProcessNodeModel> nodes = new HashMap<>();

    public void addNode(ProcessNodeModel processNodeModel) {
        if (nodes.containsKey(processNodeModel.getName())) {
            throw new IllegalArgumentException("同一个流程不不能顶一个多个相同id的节点");
        }
        nodes.put(processNodeModel.getName(), processNodeModel);
    }

    public void check() {
        int startNode = 0;
        for (ProcessNodeModel processNodeModel : nodes.values()) {
            String className = processNodeModel.getClassName();
            try {
                Class.forName(className);
            } catch (Throwable e) {
                throw new IllegalArgumentException("无法加载节点[" + processNodeModel.getName() + "]的类：" + className);
            }
            String nextNode = processNodeModel.getNextNode();
            if (nextNode != null) {
                String[] nextNodes = nextNode.split(",");
                for (String nodeName : nextNodes) {
                    if (!nodes.containsKey(nodeName)) {
                        throw new IllegalArgumentException("节点[name=" + nodeName + "]不存在");
                    }
                }
            }
            if (processNodeModel.getBegin()) {
                startNode++;
            }
        }
        if (startNode != 1) {
            throw new IllegalArgumentException("不合法的流程，每个流程只能有一个开始节点");
        }
    }

    public ProcessorDefinition build(ProcessInstanceCreator creator) throws Exception {
        Map<String, ProcessorNode> processorNodeMap = new HashMap<>();
        ProcessorDefinition processorDefinition = new ProcessorDefinition();
        processorDefinition.setName(name);
        // 第一次循环，将所有的processNode转化为processorNode实例，并保存在集合中，并将第一个节点放入processorDefinition
        for (ProcessNodeModel processNodeModel : nodes.values()) {
            String className = processNodeModel.getClassName();
            Processor processor = creator.newInstance(className, processNodeModel.getName());
            ProcessorNode processorNode = new ProcessorNode();
            processorNode.setProcessor(processor);
            processorNode.setName(processNodeModel.getName());
            if (processNodeModel.getBegin()) {
                processorDefinition.setFirst(processorNode);
            }
            processorNode.setInvokeMethod(processNodeModel.getInvokeMethod());
            processorNodeMap.put(processNodeModel.getName(), processorNode);
        }

        // 第二次循环，将所有节点建立关联关系
        for (ProcessorNode processNode : processorNodeMap.values()) {
            String nextNodeStr = nodes.get(processNode.getName()).getNextNode();
            if (nextNodeStr == null) {
                continue;
            }
            String[] nextNodes = nextNodeStr.split(",");
            for (String nextNode : nextNodes) {
                processNode.addNextNode(processorNodeMap.get(nextNode));
            }
        }
        return processorDefinition;
    }
}