package com.zhl.example.processengine.wrapper.annotations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author hailang.zhang
 * @since 2023-06-06
 */
@Configuration
@ComponentScan(value = "com.zhl.example.processengine.wrapper.instance")
public class ComponentScanConfig {
}