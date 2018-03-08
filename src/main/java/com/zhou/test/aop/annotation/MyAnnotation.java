package com.zhou.test.aop.annotation;

import java.lang.annotation.*;

/**
 * @author eli
 * @date 2017/12/4 15:07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAnnotation {
}
