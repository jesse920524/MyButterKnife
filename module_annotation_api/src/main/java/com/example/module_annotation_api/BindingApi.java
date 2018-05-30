package com.example.module_annotation_api;

import android.app.Activity;
import android.view.View;

import com.example.module_compiler.one.ProxyInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Jesse Fu on 2018/5/25.
 *
 * @desc 实现绑定注入的类
 */

public class BindingApi {
    /**
     * 用来缓存反射出来的类，节省每次都去反射引起的性能问题
     */
    static final Map<Class<?>, Constructor<?>> BINDINGS = new LinkedHashMap<>();

    public static void inject(Activity activity){
        inject(activity, activity.getWindow().getDecorView());
    }

    /**使用反射的方式调用编译时生成的类的构造器*/
    public static void inject(Activity host, View root){
        String classFullName = host.getClass().getName() + ProxyInfo.CLASS_SUFFIX;
        Constructor constructor = BINDINGS.get(host.getClass());

        try {
            if (constructor == null){
                Class proxy = Class.forName(classFullName);
                constructor = proxy.getDeclaredConstructor(host.getClass(), View.class);
                BINDINGS.put(host.getClass(), constructor);

            }

            constructor.setAccessible(true);
            constructor.newInstance(host, root);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
