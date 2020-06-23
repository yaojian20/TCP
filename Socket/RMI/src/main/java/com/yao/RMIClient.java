package com.yao;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * @author yaojian
 * @date 2020/6/23 22:34
 */
public class RMIClient {

    public static void main(String[] args) {
        try {
            RMIRemote rmiRemote = (RMIRemote) Naming.lookup("rmi://localhost:8888/sayHello");

            System.out.println(rmiRemote.sayHello("hello,meimei"));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
