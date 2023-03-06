package com.zhl.gen.core.processor;

import com.google.auto.service.AutoService;
import com.google.common.base.CaseFormat;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
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
public class ServiceImplCodeGenProcessor extends BaseCodeGenProcessor {

    public static final String SUFFIX = "ServiceImpl";

    @Override
    protected void generateClass(TypeElement typeElement, RoundEnvironment roundEnvironment) {
        DefaultNameContext nameContext = getNameContext(typeElement);

        String className = typeElement.getSimpleName() + SUFFIX;

        //注入参数
        String qryExeName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
                nameContext.getQryExeClassName());
        String cmdExeName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
                nameContext.getCmdExeClassName());
        String converterName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
                nameContext.getConverterClassName());
        FieldSpec qryExe = FieldSpec
                .builder(ClassName.get(nameContext.getQryExePackageName(),
                        nameContext.getQryExeClassName()), qryExeName)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build();
        FieldSpec cmdExe = FieldSpec
                .builder(ClassName.get(nameContext.getCmdExePackageName(),
                        nameContext.getCmdExeClassName()), cmdExeName)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build();
        FieldSpec converter = FieldSpec
                .builder(ClassName.get(nameContext.getConverterPackageName(),
                        nameContext.getConverterClassName()), converterName)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build();

        //构造函数
        MethodSpec.Builder constructorSpecBuilder = MethodSpec.constructorBuilder()
                .addParameter(ClassName.get(nameContext.getQryExePackageName(),
                        nameContext.getQryExeClassName()), qryExeName)
                .addParameter(ClassName.get(nameContext.getCmdExePackageName(),
                        nameContext.getCmdExeClassName()), cmdExeName)
                .addParameter(ClassName.get(nameContext.getConverterPackageName(),
                        nameContext.getConverterClassName()), converterName)
                .addModifiers(Modifier.PUBLIC);

        constructorSpecBuilder
                .addStatement("this.$L = $L", qryExeName, qryExeName)
                .addStatement("this.$L = $L", cmdExeName, cmdExeName)
                .addStatement("this.$L = $L", converterName, converterName)
        ;

        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .addField(qryExe)
                .addField(cmdExe)
                .addField(converter)
                .addSuperinterface(
                        ClassName.get(nameContext.getServicePackageName(), nameContext.getServiceClassName()))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Service.class);

        builder.addMethod(constructorSpecBuilder.build());
        String packageName = generatePackage(typeElement);
        genJavaSourceFile(packageName, typeElement.getAnnotation(GenServiceImpl.class).sourcePath(), builder);
    }

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return GenServiceImpl.class;
    }

    @Override
    public String generatePackage(TypeElement typeElement) {
        return typeElement.getAnnotation(GenServiceImpl.class).pkgName();
    }
}