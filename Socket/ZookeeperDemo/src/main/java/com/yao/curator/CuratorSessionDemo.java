package com.yao.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author yaojian
 * @date 2020/7/21 22:54
 */
public class CuratorSessionDemo {


    private final static String CONNECTTIONURL = "192.168.88.88:2181,192.168.88.89:2181" +
            "192.168.88.90:2181,192.168.88.91:2181";
    public static void main(String[] args) {

        //两种创建方式
        //1.normal
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(CONNECTTIONURL,5000,5000,new ExponentialBackoffRetry(1000,3));


        //2.fluent风格,即链式结构
        CuratorFramework curatorFramework1 = CuratorFrameworkFactory.builder().connectString(CONNECTTIONURL).sessionTimeoutMs(5000).connectionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
    }
}
