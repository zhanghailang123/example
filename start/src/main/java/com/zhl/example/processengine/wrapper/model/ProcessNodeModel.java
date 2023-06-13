package com.zhl.example.processengine.wrapper.model;

import com.zhl.example.processengine.enums.InvokeMethod;
import lombok.Data;

/**
 * @author hailang.zhang
 * @since 2023-06-06
 */
@Data
public class ProcessNodeModel {
    private String name;
    private String className;
    private String nextNode;
    private Boolean begin = false;
    private InvokeMethod invokeMethod;
}