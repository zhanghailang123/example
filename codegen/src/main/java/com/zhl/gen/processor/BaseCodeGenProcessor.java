package com.zhl.gen.processor;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.zhl.gen.annotation.FieldDesc;
import com.zhl.gen.annotation.TypeConverter;
import com.zhl.gen.context.ProcessingEnvironmentHolder;
import com.zhl.gen.core.GenCmdExe;
import com.zhl.gen.core.GenConverter;
import com.zhl.gen.core.GenQryExe;
import com.zhl.gen.core.GenService;
import com.zhl.gen.core.processor.CmdExeCodeGenProcessor;
import com.zhl.gen.core.processor.ConverterCodeGenProcessor;
import com.zhl.gen.core.processor.QryExeCodeGenProcessor;
import com.zhl.gen.core.processor.ServiceCodeGenProcessor;
import com.zhl.gen.spi.CodeGenProcessor;
import com.zhl.gen.core.processor.GatewayCodeGenProcessor;
import com.zhl.gen.core.GenDO;
import com.zhl.gen.core.processor.DoCodeGenProcessor;
import com.zhl.gen.core.GenGateway;
import lombok.Data;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;
import javax.xml.validation.Schema;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author gim
 */
public abstract class BaseCodeGenProcessor implements CodeGenProcessor {

  public static final String PREFIX = "Base";

  @Override
  public void generate(TypeElement typeElement, RoundEnvironment roundEnvironment)
      throws Exception {
    //添加其他逻辑扩展
    generateClass(typeElement, roundEnvironment);
  }

  /**
   * 生成class 类
   *
   * @param typeElement
   * @param roundEnvironment
   * @return
   */
  protected abstract void generateClass(TypeElement typeElement,
      RoundEnvironment roundEnvironment);

  /**
   * 过滤属性
   *
   * @param typeElement
   * @param predicate
   * @return
   */
  public Set<VariableElement> findFields(TypeElement typeElement,
      Predicate<VariableElement> predicate) {
    List<? extends Element> fieldTypes = typeElement.getEnclosedElements();
    Set<VariableElement> variableElements = new LinkedHashSet<>();
    for (VariableElement e : ElementFilter.fieldsIn(fieldTypes)) {
      if (predicate.test(e)) {
        variableElements.add(e);
      }
    }
    return variableElements;
  }

  /**
   * 获取名称默认上下文
   *
   * @param typeElement
   * @return
   */
  public DefaultNameContext getNameContext(TypeElement typeElement) {
    DefaultNameContext context = new DefaultNameContext();

    String serviceName =typeElement.getSimpleName() + ServiceCodeGenProcessor.SUFFIX;
    String gateWayName =typeElement.getSimpleName() + GatewayCodeGenProcessor.SUFFIX;
    String doName = typeElement.getSimpleName() + DoCodeGenProcessor.SUFFIX;
    String cmdExeName = typeElement.getSimpleName() + CmdExeCodeGenProcessor.SUFFIX;
    String qryExeName = typeElement.getSimpleName() + QryExeCodeGenProcessor.SUFFIX;
    String converterName = typeElement.getSimpleName() + ConverterCodeGenProcessor.SUFFIX;
    context.setServiceClassName(serviceName);
    context.setGateWayClassName(gateWayName);
    context.setDoClassName(doName);
    context.setQryExeClassName(qryExeName);
    context.setCmdExeClassName(cmdExeName);
    context.setConverterClassName(converterName);
    Optional.ofNullable(typeElement.getAnnotation(GenDO.class)).ifPresent(anno -> {
      context.setDoPackageName(anno.pkgName());
    });
    Optional.ofNullable(typeElement.getAnnotation(GenGateway.class)).ifPresent(anno -> {
      context.setServicePackageName(anno.pkgName());
    });
    Optional.ofNullable(typeElement.getAnnotation(GenService.class)).ifPresent(anno -> {
      context.setGateWayPackageName(anno.pkgName());
    });
    Optional.ofNullable(typeElement.getAnnotation(GenQryExe.class)).ifPresent(anno -> {
      context.setQryExePackageName(anno.pkgName());
    });
    Optional.ofNullable(typeElement.getAnnotation(GenCmdExe.class)).ifPresent(anno -> {
      context.setCmdExePackageName(anno.pkgName());
    });
    Optional.ofNullable(typeElement.getAnnotation(GenConverter.class)).ifPresent(anno -> {
      context.setConverterPackageName(anno.pkgName());
    });
    return context;
  }

  /**
   * 获取父类
   *
   * @param element
   * @return
   */
  public TypeElement getSuperClass(TypeElement element) {
    TypeMirror parent = element.getSuperclass();
    if (parent instanceof DeclaredType) {
      Element elt = ((DeclaredType) parent).asElement();
      if (elt instanceof TypeElement) {
        return (TypeElement) elt;
      }
    }
    return null;
  }

  public void addSetterAndGetterMethod(TypeSpec.Builder builder,
      Set<VariableElement> variableElements) {
    for (VariableElement ve : variableElements) {
      TypeName typeName = TypeName.get(ve.asType());
//      FieldSpec.Builder fieldSpec = FieldSpec
//              .builder(typeName, ve.getSimpleName().toString(), Modifier.PRIVATE)
//              .addAnnotation(AnnotationSpec.builder(Schema.class)
//                      .addMember("title", "$S", getFieldDesc(ve))
//                      .build());

//      FieldSpec.Builder fieldSpec = FieldSpec
//              .builder(typeName, ve.getSimpleName().toString(), Modifier.PRIVATE);
//      builder.addField(fieldSpec.build());
      String fieldName = getFieldDefaultName(ve);
      MethodSpec.Builder getMethod = MethodSpec.methodBuilder("get" + fieldName)
          .returns(typeName)
          .addModifiers(Modifier.PUBLIC)
          .addStatement("return $L", ve.getSimpleName().toString());
      MethodSpec.Builder setMethod = MethodSpec.methodBuilder("set" + fieldName)
          .returns(void.class)
          .addModifiers(Modifier.PUBLIC)
          .addParameter(typeName, ve.getSimpleName().toString())
          .addStatement("this.$L = $L", ve.getSimpleName().toString(),
              ve.getSimpleName().toString());
      builder.addMethod(getMethod.build());
      builder.addMethod(setMethod.build());
    }
  }

  /**
   * 应用转化器
   * @param builder
   * @param variableElements
   */
  public void addSetterAndGetterMethodWithConverter(TypeSpec.Builder builder,
      Set<VariableElement> variableElements) {
    for (VariableElement ve : variableElements) {
      TypeName typeName;
      if (Objects.nonNull(ve.getAnnotation(TypeConverter.class))) {
        //这里处理下泛型的情况，比如List<String> 这种，TypeConverter FullName 用逗号分隔"java.lang.List
        String fullName = ve.getAnnotation(TypeConverter.class).toTypeFullName();
        Iterable<String> classes = Splitter.on(",").split(fullName);
        int size = Iterables.size(classes);
        if(size > 1){
          //泛型生成像这样
          //ParameterizedTypeName.get(ClassName.get(JsonObject.class), ClassName.get(String.class))
          typeName = ParameterizedTypeName.get(ClassName.bestGuess(Iterables.get(classes,0)),ClassName.bestGuess(Iterables.get(classes,1)));
        }else {
          typeName = ClassName.bestGuess(ve.getAnnotation(TypeConverter.class).toTypeFullName());
        }
      } else {
        typeName = TypeName.get(ve.asType());
      }
      FieldSpec.Builder fieldSpec = FieldSpec
          .builder(typeName, ve.getSimpleName().toString(), Modifier.PRIVATE)
          .addAnnotation(AnnotationSpec.builder(Schema.class)
              .addMember("title", "$S", getFieldDesc(ve))
              .build());
      builder.addField(fieldSpec.build());
      String fieldName = getFieldDefaultName(ve);
      MethodSpec.Builder getMethod = MethodSpec.methodBuilder("get" + fieldName)
          .returns(typeName)
          .addModifiers(Modifier.PUBLIC)
          .addStatement("return $L", ve.getSimpleName().toString());
      MethodSpec.Builder setMethod = MethodSpec.methodBuilder("set" + fieldName)
          .returns(void.class)
          .addModifiers(Modifier.PUBLIC)
          .addParameter(typeName, ve.getSimpleName().toString())
          .addStatement("this.$L = $L", ve.getSimpleName().toString(),
              ve.getSimpleName().toString());
      builder.addMethod(getMethod.build());
      builder.addMethod(setMethod.build());
    }
  }


  protected void addIdSetterAndGetter(TypeSpec.Builder builder){
    MethodSpec.Builder getMethod = MethodSpec.methodBuilder("getId")
        .returns(ClassName.get(Long.class))
        .addModifiers(Modifier.PUBLIC)
        .addStatement("return $L", "id");
    MethodSpec.Builder setMethod = MethodSpec.methodBuilder("setId")
        .returns(void.class)
        .addModifiers(Modifier.PUBLIC)
        .addParameter(TypeName.LONG,"id")
        .addStatement("this.$L = $L", "id","id");
    builder.addMethod(getMethod.build());
    builder.addMethod(setMethod.build());
  }

  protected String getFieldDesc(VariableElement ve) {
    return Optional.ofNullable(ve.getAnnotation(FieldDesc.class))
        .map(s -> s.name()).orElse(ve.getSimpleName().toString());
  }

  protected String getFieldDefaultName(VariableElement ve) {
    return ve.getSimpleName().toString().substring(0, 1).toUpperCase() + ve.getSimpleName()
        .toString().substring(1);
  }


  public void genJavaSourceFile(String packageName, String pathStr,
      TypeSpec.Builder typeSpecBuilder) {
    TypeSpec typeSpec = typeSpecBuilder.build();
    JavaFile javaFile = JavaFile
            .builder(packageName, typeSpec)
            .addFileComment("---Auto Generated by zhl ---")
            .build();
    String packagePath =
            packageName.replace(".", File.separator) + File.separator + typeSpec.name + ".java";
    try {
      Path path = Paths.get(pathStr);
      File file = new File(path.toFile().getAbsolutePath());
      if (!file.exists()) {
        return;
      }
      String sourceFileName = path.toFile().getAbsolutePath() + File.separator + packagePath;
      File sourceFile = new File(sourceFileName);
      if (!sourceFile.exists()) {
        javaFile.writeTo(file);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public TypeSpec.Builder getSourceType(String sourceName, String packageName,
      String superClassName) {
    TypeSpec.Builder sourceBuilder = TypeSpec.classBuilder(sourceName)
        .superclass(ClassName.get(packageName, superClassName))
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(Schema.class)
        .addAnnotation(Data.class);
    return sourceBuilder;
  }

  public TypeSpec.Builder getSourceTypeWithConstruct(TypeElement e, String sourceName,
      String packageName, String superClassName) {
    MethodSpec.Builder constructorSpecBuilder = MethodSpec.constructorBuilder()
        .addParameter(TypeName.get(e.asType()), "source")
        .addModifiers(Modifier.PUBLIC);
    constructorSpecBuilder.addStatement("super(source)");
    TypeSpec.Builder sourceBuilder = TypeSpec.classBuilder(sourceName)
        .superclass(ClassName.get(packageName, superClassName))
        .addModifiers(Modifier.PUBLIC)
        .addMethod(MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .build())
        .addMethod(constructorSpecBuilder.build())
        .addAnnotation(Schema.class)
        .addAnnotation(Data.class);
    return sourceBuilder;
  }


  protected void genJavaFile(String packageName, TypeSpec.Builder typeSpecBuilder) {
    JavaFile javaFile = JavaFile.builder(packageName, typeSpecBuilder.build())
        .addFileComment("---Auto Generated by zhl ---").build();
    try {
      javaFile.writeTo(ProcessingEnvironmentHolder.getEnvironment().getFiler());
    } catch (IOException e) {
      ProcessingEnvironmentHolder.getEnvironment().getMessager()
          .printMessage(Kind.ERROR, e.getMessage());
    }
  }

}
