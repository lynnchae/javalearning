package org.annotation;

import java.lang.annotation.*;

/**

 * Class Name : org.lynn.annotation
 * Description :
 * @author : cailinfeng
 * Date : 2018/6/26 16:06
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoWired {

    String value() default  "";

}
