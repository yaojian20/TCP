package Serizable;

import java.io.Serializable;

/**
 * @author yaojian
 * @date 2020/6/16 21:36
 */

//如果父类没有实现序列化，那么父类的成员变量也不会被序列化
    //序列化再反序列化可实现深克隆
public class Person implements Serializable {
    private static final long serialVersionUID = 8433660785139039727L;

    //序列化不保存静态变量
    public static  String address = "jiangsu";
    private int age;


    //transient表示该属性不参加序列化
    private transient String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
