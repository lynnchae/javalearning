package org.lynn.annotation;

import java.lang.annotation.*;

/**
 * Copyright @ 2013QIANLONG.
 * All right reserved.
 * Class Name : org.lynn.annotation
 * Description :
 * Author : cailinfeng
 * Date : 2018/6/26 16:06
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoWired {

    String value() default  "";

}
