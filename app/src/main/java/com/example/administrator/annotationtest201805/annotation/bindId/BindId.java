package com.example.administrator.annotationtest201805.annotation.bindId;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

/**
 * Created by Jesse Fu on 2018/5/23.
 * @author Administrator
 *
 * @time 2018年5月23日 10:40:26
 *
 * @desc bind view's id
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BindId {
    int value() default 0;
}
