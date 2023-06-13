package com.zhl.example.processengine.wrapper.config;

import com.zhl.example.processengine.wrapper.model.ProcessModel;

import java.util.List;

/**
 * @author hailang.zhang
 * @since 2023-06-06
 */
public interface ProcessParser {

    /**
     * 解析器
     * @return
     * @throws Exception
     */
    List<ProcessModel> parse() throws Exception;
}