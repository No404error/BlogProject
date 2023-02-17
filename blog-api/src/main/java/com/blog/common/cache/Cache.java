package com.blog.common.cache;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    //默认过期一分钟
    long expire() default 1 * 60 * 1000;

    String name() default "";

}