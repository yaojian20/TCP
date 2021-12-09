package com.yao.distributeLock.javvaAPILock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author yaojian
 * @date 2020/7/25 21:41
 */
public class ZookeeperClient {

    private final static String CONNECTTIONURL = "192.168.88.88:2181,192.168.88.89:2181" +
            "192.168.88.90:2181,192.168.88.91:2181";

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public final static int sessionTimeout = 5000;


    public static ZooKeeper getInstance() throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECTTIONURL, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected){

            //成功链接
                    countDownLatch.countDown();
                }
            }
        });



        countDownLatch.await();

        return zooKeeper;
    }
}
