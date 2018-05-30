package com.example.administrator.annotationtest201805.annotation.bindId;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jesse Fu on 2018/5/23.
 *
 * 执行绑定控件id逻辑的api
 */

public class BindIdApi {

    private static final String FIND_VIEW_BY_ID = "findViewById";
    public static void bind(Activity activity){
        Class<?> clazz = activity.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field :
                fields) {
            if (field.isAnnotationPresent(BindId.class)){
                 BindId bindId = field.getAnnotation(BindId.class);
                 int id = bindId.value();

                try {
                    Method method = clazz.getMethod(FIND_VIEW_BY_ID, int.class);
                    method.setAccessible(true);
                    Object view = method.invoke(activity, id);

                    field.setAccessible(true);
                    field.set(activity, view);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
