package com.zhl.gen.core.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.TypeSpec;
import com.zhl.gen.core.GenDO;
import com.zhl.gen.core.GenGateway;
import com.zhl.gen.processor.BaseCodeGenProcessor;
import com.zhl.gen.processor.DefaultNameContext;
import com.zhl.gen.spi.CodeGenProcessor;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;


/**
 * @author hailang.zhang
 * @since 2023-03-06
 */
@AutoService(value = CodeGenProcessor.class)
public class GatewayCodeGenProcessor extends BaseCodeGenProcessor {

    public static final String SUFFIX = "Gateway";

    @Override
    protected void generateClass(TypeElement typeElement, RoundEnvironment roundEnvironment) {
        DefaultNameContext nameContext = getNameContext(typeElement);

        String className = typeElement.getSimpleName() + SUFFIX;
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(className)
                .addModifiers(Modifier.PUBLIC);
        String packageName = generatePackage(typeElement);
        genJavaSourceFile(packageName, typeElement.getAnnotation(GenDO.class).sourcePath(), builder);

    }

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return GenGateway.class;
    }

    @Override
    public String generatePackage(TypeElement typeElement) {
        return typeElement.getAnnotation(GenGateway.class).pkgName();
    }
}