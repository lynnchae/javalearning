package org.annotation;

import java.lang.annotation.*;

/**

 * Class Name : org.lynn.annotation
 * Description :
 * @author : cailinfeng
 * Date : 2018/6/26 16:07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {

    String value() default  "";

}
