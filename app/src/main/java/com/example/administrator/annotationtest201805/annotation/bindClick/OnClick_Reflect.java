package com.example.administrator.annotationtest201805.annotation.bindClick;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Jesse Fu on 2018/5/23.
 * 注解OnClick标记的方法响应控件的点击事件
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick_Reflect {

    int value() default -1;
}
