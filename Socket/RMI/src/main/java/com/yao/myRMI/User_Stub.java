package com.yao.myRMI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author yaojian
 * @date 2020/6/27 21:07
 */
public class User_Stub extends  User {

    private Socket socket;

    public User_Stub() throws IOException {
        socket = new Socket("localhost", 8888);
    }

    public  int  getAge() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        //表示要调用的远程服务端的方法
        objectOutputStream.writeObject("age");
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        int age = inputStream.readInt();

        return  age;


    }
}
