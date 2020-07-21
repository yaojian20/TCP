package com.yao.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author yaojian
 * @date 2020/7/21 22:20
 */
public class SessionDemo {

    private final static String CONNECTTIONURL = "192.168.88.88:2181,192.168.88.89:2181" +
            "192.168.88.90:2181,192.168.88.91:2181";
    public static void main(String[] args) {

        ZkClient zkClient = new ZkClient(CONNECTTIONURL,4000);



    }
}
