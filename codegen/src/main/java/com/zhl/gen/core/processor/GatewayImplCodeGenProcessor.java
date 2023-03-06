package com.zhl.gen.core.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import com.zhl.gen.core.GenDO;
import com.zhl.gen.core.GenGatewayImpl;
import com.zhl.gen.core.GenServiceImpl;
import com.zhl.gen.processor.BaseCodeGenProcessor;
import com.zhl.gen.processor.DefaultNameContext;
import com.zhl.gen.spi.CodeGenProcessor;
import org.springframework.stereotype.Service;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;

/**
 * @author hailang.zhang
 * @since 2023-03-03
 */
@AutoService(value = CodeGenProcessor.class)
public class GatewayImplCodeGenProcessor extends BaseCodeGenProcessor {

    public static final String SUFFIX = "GatewayImpl";

    @Override
    protected void generateClass(TypeElement typeElement, RoundEnvironment roundEnvironment) {
        DefaultNameContext nameContext = getNameContext(typeElement);

        String className = typeElement.getSimpleName() + SUFFIX;
        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .addSuperinterface(
                        ClassName.get(nameContext.getServicePackageName(), nameContext.getServiceClassName()))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Service.class);
        String packageName = generatePackage(typeElement);
        genJavaSourceFile(packageName, typeElement.getAnnotation(GenServiceImpl.class).sourcePath(), builder);
    }

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return GenGatewayImpl.class;
    }

    @Override
    public String generatePackage(TypeElement typeElement) {
        return typeElement.getAnnotation(GenServiceImpl.class).pkgName();
    }
}