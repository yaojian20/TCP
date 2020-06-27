package com.yao.myRMI;

import java.io.IOException;

/**
 * @author yaojian
 * @date 2020/6/27 21:33
 */
public class UserClient {

    public static void main(String[] args) throws IOException {
        User user = new User_Stub();
        int age = user.getAge();
        System.out.println(age);
    }
}
