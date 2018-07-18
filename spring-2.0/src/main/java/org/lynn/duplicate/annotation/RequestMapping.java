package org.lynn.duplicate.annotation;

import java.lang.annotation.*;

/**

 * Class Name : org.lynn.annotation
 * Description :
 * @author : cailinfeng
 * Date : 2018/6/26 16:07
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value() default  "";

}
