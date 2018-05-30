package com.example.module_compiler.one;

import com.example.module_annotation.BindView;
import com.example.module_annotation.OnClick;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by Jesse Fu on 2018/5/24.
 */
@AutoService(Processor.class)
public class Compiler extends AbstractProcessor {

    private Filer mFilerUtils;//????????????JavaSourceCode.
    private Elements mElementUtils;//???????????????????????????
    private Messager messager;//??????????

    /**???????? key?????, value???????????*/
    private Map<String, ProxyInfo> proxyInfoMap = new HashMap<>();

    /**?????????*/
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFilerUtils = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
    }

    /**?????Java????*/
    @Override
    public SourceVersion getSupportedSourceVersion() {
//        return super.getSupportedSourceVersion();
        return SourceVersion.RELEASE_8;
    }

    /**?? Processor ?????*/
    @Override
    public Set<String> getSupportedAnnotationTypes() {
//        return super.getSupportedAnnotationTypes();
        Set<String> annotationTypeSet = new LinkedHashSet<>();
        annotationTypeSet.add(BindView.class.getCanonicalName());
        annotationTypeSet.add(OnClick.class.getCanonicalName());
        return annotationTypeSet;
    }

    /**????,????
     * 1.?????????????
     * 2.?????????Java??*/
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        collectionInfo(roundEnvironment);
        generateClass();
        return true;
    }

    /**??????????*/
    private void collectionInfo(RoundEnvironment roundEnvironment){
        proxyInfoMap.clear();

        /**???BindView???field*/
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);

        /**????*/
        for (Element element :
                elements) {
            if (element.getKind() == ElementKind.FIELD){
                BindView bindView = element.getAnnotation(BindView.class);
                if (bindView != null){
                    int value = bindView.value();

                    /**??????*/
                    VariableElement variableElement = (VariableElement) element;

                    /**??????*/
                    String qualifiedName = ((TypeElement)variableElement.getEnclosingElement()).getQualifiedName().toString();
                    /**??*/
                    String clsName = variableElement.getSimpleName().toString();

                    /**????*/
                    String pkgName = mElementUtils.getPackageOf(variableElement).getQualifiedName().toString();


                    /**?????*/
                    ProxyInfo proxyInfo = proxyInfoMap.get(qualifiedName);
                    if (proxyInfo == null){
                        proxyInfo = new ProxyInfo();
                        proxyInfoMap.put(qualifiedName, proxyInfo);
                    }

                    proxyInfo.mInjectElements.put(value, variableElement);

                    proxyInfo.value = value;
                    proxyInfo.typeElement = (TypeElement) variableElement.getEnclosingElement();
                    proxyInfo.packageName = pkgName;
                }
            }else{
                continue;
            }

        }

        /**???????????*/
        Set<? extends Element> elementsMethods = roundEnvironment.getElementsAnnotatedWith(OnClick.class);
        for (Element element :
                elementsMethods) {

            if (element.getKind() == ElementKind.METHOD){
                /**??????*/
                OnClick onClick = element.getAnnotation(OnClick.class);
                if (onClick != null){
                    int value = onClick.value();

                    ExecutableElement executableElement = (ExecutableElement) element;
                    String qualifiedName = ((TypeElement)executableElement.getEnclosingElement()).getQualifiedName().toString();
                    ProxyInfo proxyInfo = proxyInfoMap.get(qualifiedName);
                    if (proxyInfo == null){
                        proxyInfo = new ProxyInfo();
                        proxyInfoMap.put(qualifiedName, proxyInfo);
                    }

                    proxyInfo.mInjectMethods.put(value, executableElement);
                }
            }else{
                continue;
            }
        }
    }

    /**?????*/
    private void generateClass(){
        for (String key : proxyInfoMap.keySet()){
            ProxyInfo proxyInfo = proxyInfoMap.get(key);
            JavaFileObject sourceFile = null;
            try {
                sourceFile = mFilerUtils.createSourceFile(proxyInfo.getProxyClassFullName(), proxyInfo.typeElement);
                Writer writer = sourceFile.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
//                e.printStackTrace();
                error(proxyInfo.typeElement, "===tb===%s", e.getMessage());
            }
        }
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
