package com.zhl.gen.core.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zhl.gen.core.GenDOConverter;
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
public class DoConverterCodeGenProcessor extends BaseCodeGenProcessor {

    public static final String SUFFIX = "DOConverter";

    @Override
    protected void generateClass(TypeElement typeElement, RoundEnvironment roundEnvironment) {
        DefaultNameContext nameContext = getNameContext(typeElement);

        String className = typeElement.getSimpleName() + SUFFIX;
        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Service.class);
        String packageName = generatePackage(typeElement);
        MethodSpec.Builder getMethod = MethodSpec.methodBuilder("convert2" + typeElement.getSimpleName().toString() + "DO")
                .returns(ClassName.get(nameContext.getDoPackageName(), nameContext.getDoClassName()))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(
                        ClassName.get(packageName, typeElement.getSimpleName().toString()),
                        "source")
                .addStatement("return $L", nameContext.getDoClassName());
        builder.addMethod(getMethod.build());

        MethodSpec.Builder getMethod2 = MethodSpec.methodBuilder("convert2" + typeElement.getSimpleName().toString())
                .returns(ClassName.get(typeElement).topLevelClassName())
                .addJavadoc("test ,%s", "111")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(
                        ClassName.get(nameContext.getDoPackageName(), nameContext.getDoClassName()),
                        "source")
                .addStatement("return $L", typeElement.getSimpleName().toString());
        builder.addMethod(getMethod2.build());

        genJavaSourceFile(packageName, typeElement.getAnnotation(GenDOConverter.class).sourcePath(), builder);
    }

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return GenDOConverter.class;
    }

    @Override
    public String generatePackage(TypeElement typeElement) {
        return typeElement.getAnnotation(GenDOConverter.class).pkgName();
    }
}