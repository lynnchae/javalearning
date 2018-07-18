package org.lynn.duplicate.annotation;

import java.lang.annotation.*;

/**
 * Class Name : org.lynn.annotation
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/18 10:50
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ShieldDuplicateRequest {

    int timeout() default 1000;

}
