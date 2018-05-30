package com.example.administrator.annotationtest201805.annotation.bindClick;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jesse Fu on 2018/5/23.
 */

public class BindOnClickApi {

    public static void bind(final Activity activity){
        Class<?> clazz = activity.getClass();

        Method[] methods = clazz.getDeclaredMethods();

        for (final Method method :
                methods) {

            if (method.isAnnotationPresent(OnClick_Reflect.class)){
                OnClick_Reflect onClickReflect = method.getAnnotation(OnClick_Reflect.class);

                final View view = activity.findViewById(onClickReflect.value());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            method.setAccessible(true);
                            method.invoke(activity, view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }
    }
}
