package com.yao.myRMI;

import java.io.IOException;

/**
 * @author yaojian
 * @date 2020/6/27 21:05
 */
public class User {

    private  int age;


    public int getAge() throws IOException {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
