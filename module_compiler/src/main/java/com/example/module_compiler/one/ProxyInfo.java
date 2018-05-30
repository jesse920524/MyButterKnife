package com.example.module_compiler.one;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Created by Jesse Fu on 2018/5/24.
 *
 * @desc ????? ?????
 */

public class ProxyInfo {
    private static final String TAG = "ProxyInfo";

    /**?*/
    public TypeElement typeElement;

    /**?????*/
    public int value;

    public String packageName;

    /**
     * key: id ???????? value: ???????*/
    public Map<Integer, VariableElement> mInjectElements = new HashMap<>();

    /**key: id ?????? value: ?????*/
    public Map<Integer, ExecutableElement> mInjectMethods = new HashMap<>();

    public static final String PROXY = "TA";
    public static final String CLASS_SUFFIX = "_" + PROXY;

    public String getProxyClassFullName(){
        return typeElement.getQualifiedName().toString() + CLASS_SUFFIX;
    }

    public String getClassName() {
        return typeElement.getSimpleName().toString() + CLASS_SUFFIX;
    }

    /**???*/
    public String generateJavaCode(){
        StringBuilder sb = new StringBuilder();
        sb.append("//???????????!!!\n");
        sb.append("package ").append(packageName).append(";\n\n");
        sb.append("import com.example.module_annotation_api.*;\n");
        sb.append("import android.support.annotation.Keep;\n");
        sb.append("import android.view.View;\n");
        sb.append("import " + typeElement.getQualifiedName() + ";\n");
        sb.append("\n");
        sb.append("@Keep").append("\n");//????
        sb.append("public class ").append(getClassName());
        sb.append("{\n");
        generateMethod(sb);
        sb.append("}\n");

        return sb.toString();
    }

    /**????*/
    private void generateMethod(StringBuilder sb){
        sb.append("    public " + getClassName() + "(final " + typeElement.getSimpleName() + " host, View object){\n");
//        if (value > 0){
//            sb.append("    host.setContentView(" + value + ");\n");
//        }

        for (int id: mInjectElements.keySet()){
            VariableElement variableElement = mInjectElements.get(id);
            String name = variableElement.getSimpleName().toString();
            String type = variableElement.asType().toString();
            //??object???, ?????view???
            sb.append("    host." + name).append(" = ");
            sb.append("(" + type +")object.findViewById(" + id +");\n");
        }

        for (int id: mInjectMethods.keySet()){
            ExecutableElement executableElement = mInjectMethods.get(id);
            VariableElement variableElement = mInjectElements.get(id);
            String name = variableElement.getSimpleName().toString();
            sb.append("    host." + name + ".setOnClickListener(new View.OnClickListener(){\n");
            sb.append("      @Override\n");
            sb.append("      public void onClick(View v){\n");
            sb.append("                host." + executableElement.getSimpleName().toString() + "(host." + name + ");\n");
            sb.append("            }\n");
            sb.append("        });\n");
        }
        sb.append("    }\n");
    }



}
