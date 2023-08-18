package com.zhl.example.processengine.wrapper.model;

import com.zhl.example.processengine.wrapper.instance.ProcessInstanceCreator;
import com.zhl.example.processengine.wrapper.instance.ReflectNodeInstanceCreator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author hailang.zhang
 * @since 2023-08-17
 */
@Slf4j
public class ProcessContextFactory {

    private static final ProcessInstanceCreator DEFAULT_INSTANCE_CREATOR = new ReflectNodeInstanceCreator();
    private List<ProcessModel> modelList;
    private final ProcessInstanceCreator instanceCreator;

    public ProcessContextFactory(List<ProcessModel> modelList) {
        this(modelList, DEFAULT_INSTANCE_CREATOR);
    }

    public ProcessContextFactory(List<ProcessModel> modelList, ProcessInstanceCreator instanceCreator) {
        this.modelList = modelList;
        this.instanceCreator = instanceCreator;
        this.init();
    }

    private void init() {

    }
}