package org.lynn.clone;

import lombok.Data;

import java.io.Serializable;

/**
 * Class Name : org.lynn.clone
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/8/6 17:27
 */
@Data
public class ClassMark implements Serializable {

    private String className;

    private Integer score;

}
