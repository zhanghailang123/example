package com.zhl.example.processengine.wrapper.model;

import com.zhl.example.processengine.node.ProcessorDefinition;
import com.zhl.example.processengine.processor.ProcessContext;
import com.zhl.example.processengine.wrapper.instance.ProcessInstanceCreator;
import com.zhl.example.processengine.wrapper.instance.ReflectNodeInstanceCreator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hailang.zhang
 * @since 2023-08-17
 */
@Slf4j
public class ProcessContextFactory {

    private static final ProcessInstanceCreator DEFAULT_INSTANCE_CREATOR = new ReflectNodeInstanceCreator();
    private List<ProcessModel> modelList;
    private final ProcessInstanceCreator instanceCreator;
    private final Map<String, ProcessorDefinition> processorDefinitionMap = new ConcurrentHashMap<>(16);

    public ProcessContextFactory(List<ProcessModel> modelList) throws Exception {
        this(modelList, DEFAULT_INSTANCE_CREATOR);
    }

    public ProcessContextFactory(List<ProcessModel> modelList, ProcessInstanceCreator instanceCreator) throws Exception {
        this.modelList = modelList;
        this.instanceCreator = instanceCreator;
        this.init();
    }

    private void init() throws Exception {
        for (ProcessModel model : modelList) {
            model.check();
        }

        for (ProcessModel model : modelList) {
            ProcessorDefinition definition = model.build(instanceCreator);
            log.info("流程构造成功");
            processorDefinitionMap.put(definition.getName(), definition);
        }
    }

    public ProcessContext getContext(String name) {
        ProcessorDefinition definition = processorDefinitionMap.get(name);
        if (definition == null) {
            throw new IllegalArgumentException("流程不存在");
        }
        return new ProcessContext(definition);
    }
}