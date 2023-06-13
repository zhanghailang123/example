package com.zhl.example.processengine.wrapper.model;

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
        for (ProcessNodeModel processNodeModel : nodes.values()) {
            String className = processNodeModel.getClassName();
            try {
                Class.forName(className);
            } catch (Exception e) {

            }
        }
    }
}