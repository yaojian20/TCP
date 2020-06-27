package com.yao.myRMI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yaojian
 * @date 2020/6/27 21:07
 */
public class User_Skeleton extends  Thread {

    private UserServer userServer;

    public User_Skeleton(UserServer userServer) {
        this.userServer = userServer;
    }

    //RMI服务端调用
    //底层是socket通信
    @Override
    public void run() {
        System.out.println("hello1");
        ServerSocket serverSocket = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(8888);
            socket = serverSocket.accept();

                inputStream = new ObjectInputStream(socket.getInputStream());

                String methond = (String) inputStream.readObject();
                System.out.println("methond is :" + methond);
                if ("age".equals(methond)) {
                    System.out.println("hello");

                    int age = userServer.getAge();
                    outputStream =  new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeInt(age);
                    outputStream.flush();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {


                try {
                    if (inputStream != null) {

                        inputStream.close();
                    }
                    if (outputStream != null){
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
