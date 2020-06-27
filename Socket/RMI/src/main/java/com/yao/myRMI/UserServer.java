package com.yao.myRMI;

/**
 * @author yaojian
 * @date 2020/6/27 21:33
 */
public class UserServer extends  User{

    public static void main(String[] args) {
        UserServer userServer = new UserServer();
        userServer.setAge(25);


        User_Skeleton skeleton = new User_Skeleton(userServer);
        skeleton.start();




    }
}
