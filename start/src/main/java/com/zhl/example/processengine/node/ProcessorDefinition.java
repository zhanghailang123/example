package com.zhl.example.processengine.node;

import com.zhl.example.processengine.processor.ProcessorUtils;
import lombok.Getter;

/**
 * @author hailang.zhang
 * @since 2023-06-02
 */
@Getter
public class ProcessorDefinition {

    private String name;

    private ProcessorNode first;

    public  ProcessorDefinition() {

    }

    public ProcessorDefinition(ProcessorNode first) {

    }

    public void setFirst(ProcessorNode first) {
        this.first = first;
        if (ProcessorUtils.hasRing(first)) {
            throw new IllegalArgumentException("Processor chain exists ring.");
        }
    }
}