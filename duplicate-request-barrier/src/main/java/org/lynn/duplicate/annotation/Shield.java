package org.lynn.duplicate.annotation;

import java.lang.annotation.*;

/**
 * Class Name : org.lynn.annotation
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/18 11:31
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Shield {

    String module();

    String operation();

}
