package com.zhl.example.processengine.wrapper.annotations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author hailang.zhang
 * @since 2023-06-06
 */
@Slf4j
public class ProcessNodeRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        String configFile = (String) metadata.getAnnotationAttributes(EnableProcessEngine.class.getName()).get("value");

    }
}