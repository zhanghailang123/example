package com.zhl.example.processengine.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程间的调用方式
 */
@Getter
@AllArgsConstructor
public enum InvokeMethod {

    ASYNC("异步"),
    SYNC("同步"),
    ;
    private String desc;
}
