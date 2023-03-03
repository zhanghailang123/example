package com.zhl.gen.vo;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.TypeSpec;
import com.zhl.gen.processor.BaseCodeGenProcessor;
import com.zhl.gen.spi.CodeGenProcessor;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

/**
 * @author hailang.zhang
 * @since 2023-03-03
 */
@AutoService(value = CodeGenProcessor.class)
public class GatewayImplCodeGenProcessor extends BaseCodeGenProcessor {

    public static final String SUFFIX = "GatewayImpl";

    @Override
    protected void generateClass(TypeElement typeElement, RoundEnvironment roundEnvironment) {
        Set<VariableElement> fields = findFields(typeElement,
                ve -> Objects.isNull(ve.getAnnotation(IgnoreVo.class)));
        String className = typeElement.getSimpleName() + SUFFIX;
        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                //        .superclass(AbstractBaseJpaVO.class)
                .addModifiers(Modifier.PUBLIC)
                //        .addAnnotation(Schema.class)

                .addAnnotation(Service.class);
        String packageName = generatePackage(typeElement);
        genJavaSourceFile(packageName, typeElement.getAnnotation(GenDO.class).sourcePath(), builder);
        //    genJavaFile(packageName, getSourceTypeWithConstruct(typeElement,sourceClassName, packageName, className));
    }

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return GenGatewayImpl.class;
    }

    @Override
    public String generatePackage(TypeElement typeElement) {
        return typeElement.getAnnotation(GenDO.class).pkgName();
    }
}