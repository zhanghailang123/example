package com.zhl.gen.vo;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import com.zhl.gen.processor.BaseCodeGenProcessor;
import com.zhl.gen.spi.CodeGenProcessor;
import lombok.Data;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

/**
 * @author gim vo 代码生成器
 */
@AutoService(value = CodeGenProcessor.class)
public class VoCodeGenProcessor extends BaseCodeGenProcessor {

  public static final String SUFFIX = "VO";

  @Override
  public Class<? extends Annotation> getAnnotation() {
    return GenDO.class;
  }

  @Override
  public String generatePackage(TypeElement typeElement) {
    return typeElement.getAnnotation(GenDO.class).pkgName();
  }

  @Override
  protected void generateClass(TypeElement typeElement, RoundEnvironment roundEnvironment) {
    Set<VariableElement> fields = findFields(typeElement,
        ve -> Objects.isNull(ve.getAnnotation(IgnoreVo.class)));
    String className = typeElement.getSimpleName() + SUFFIX;
    Builder builder = TypeSpec.classBuilder(className)
//        .superclass(AbstractBaseJpaVO.class)
        .addModifiers(Modifier.PUBLIC)
//        .addAnnotation(Schema.class)

        .addAnnotation(Data.class);
//    addSetterAndGetterMethod(builder, fields);
//    MethodSpec.Builder constructorSpecBuilder = MethodSpec.constructorBuilder()
//        .addParameter(TypeName.get(typeElement.asType()), "source")
//        .addModifiers(Modifier.PUBLIC);
//    constructorSpecBuilder.addStatement("super(source)");
//    fields.stream().forEach(f -> {
//      constructorSpecBuilder.addStatement("this.set$L(source.get$L())", getFieldDefaultName(f),
//          getFieldDefaultName(f));
//    });
//    builder.addMethod(MethodSpec.constructorBuilder()
//        .addModifiers(Modifier.PROTECTED)
//        .build());
//    builder.addMethod(constructorSpecBuilder.build());
    String packageName = generatePackage(typeElement);
    genJavaSourceFile(packageName,typeElement.getAnnotation(GenDO.class).sourcePath(), builder);
//    genJavaFile(packageName, getSourceTypeWithConstruct(typeElement,sourceClassName, packageName, className));
  }
}
