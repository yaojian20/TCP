package com.yao.distributeLock.javvaAPILock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * @author yaojian
 * @date 2020/7/25 22:49
 */
public class LockWatcher implements Watcher {
    private CountDownLatch countDownLatch;

    public LockWatcher(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDeleted){
            //监控节点删除
            countDownLatch.countDown();
        }
    }
}
