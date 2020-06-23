package com.yao;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author yaojian
 * @date 2020/6/23 22:34
 */
public class RMIServer {

    public static void main(String[] args) {

        try {
            RMIRemote rmiRemote = new RMIRemteImpl();
            //注册端口号8888
            LocateRegistry.createRegistry(8888);
            Naming.bind("rmi://localhost:8888/sayHello",rmiRemote);
            System.out.println("server init successful");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
