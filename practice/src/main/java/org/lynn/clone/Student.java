package org.lynn.clone;

import lombok.Data;

import java.io.*;

/**
 * Class Name : org.lynn.clone
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/8/6 17:26
 */
@Data
public class Student implements Serializable {

    private Integer age;

    private String name;

    private ClassMark classMark;

    public Student deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        oos.flush();
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (Student) ois.readObject();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student sa = new Student();
        sa.setAge(20);
        sa.setName("Tom");
        ClassMark classMark = new ClassMark();
        classMark.setClassName("数学");
        classMark.setScore(100);
        sa.setClassMark(classMark);
        Student sb = sa.deepClone();
        System.out.println(System.identityHashCode(sa));
        System.out.println(System.identityHashCode(sb));
        System.out.println(System.identityHashCode(sa.classMark));
        System.out.println(System.identityHashCode(sb.classMark));
        System.out.println(sa == sb);
        System.out.println(sa.classMark == sb.classMark);
    }

}
