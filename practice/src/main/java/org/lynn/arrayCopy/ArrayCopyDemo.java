package org.lynn.arrayCopy;

import java.util.Arrays;

/**
 * Class Name : org.lynn.arrayCopy
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/8/17 10:02
 */
public class ArrayCopyDemo {

    public static void main(String[] args){
        /**
         *@注 （引用对象）浅拷贝
         *@param  src      the source array.源数组
         *@param  srcPos   starting position in the source array.拷贝的起始位置
         *@param  dest     the destination array. 目标数组
         *@param  destPos  starting position in the destination data.目标数组起始位置
         *@param  length   the number of array elements to be copied.源数组中需要拷贝的数量
         */
        String[] a1 = new String[]{"1","2","3","av","test"};
        String[] a2 = new String[]{"3","4","5","6","7"};
        System.arraycopy(a1,1,a2,1,4);
        Arrays.asList(a2).stream().forEach(s -> System.out.println(s));
    }
}
