package com.yao;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author yaojian
 * @date 2020/6/23 22:30
 */
public interface RMIRemote extends Remote {

    public  String sayHello(String name) throws RemoteException;
}
