package com.yao.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author yaojian
 * @date 2020/7/23 23:11
 */
public class CuratorUtil {

    private final static String CONNECTTIONURL = "192.168.88.88:2181,192.168.88.89:2181" +
            "192.168.88.90:2181,192.168.88.91:2181";

    private static CuratorFramework curatorFramework;

    public static CuratorFramework getInstance(){
        curatorFramework = CuratorFrameworkFactory.builder().connectString(CONNECTTIONURL).sessionTimeoutMs(5000).connectionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000,3)).build();

        curatorFramework.start();
        return  curatorFramework;
    }
}
