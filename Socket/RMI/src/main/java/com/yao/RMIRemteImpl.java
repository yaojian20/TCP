package com.yao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author yaojian
 * @date 2020/6/23 22:31
 */
public class RMIRemteImpl extends UnicastRemoteObject implements RMIRemote{
    protected RMIRemteImpl() throws RemoteException {
    }

    public String sayHello(String name) throws RemoteException {
        return "yao said :" + name;
    }
}
