package com.yao.javaAPI;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author yaojian
 * @date 2020/7/21 21:20
 */
public class AuthController {

    private final static String CONNECTTIONURL = "192.168.88.88:2181,192.168.88.89:2181" +
            "192.168.88.90:2181,192.168.88.91:2181";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zookeeper;

    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        zookeeper = new ZooKeeper(CONNECTTIONURL,5000,new CreateNodeDemo());

        countDownLatch.await();//用来阻塞线程，因为zookeeper是异步的线程，等待zookeeper创建链接之后主线程再继续

        //权限：用户名+密码
        ACL acl = new ACL(ZooDefs.Perms.CREATE,new Id("digest", "root:root"));
        ACL acl1 = new ACL(ZooDefs.Perms.CREATE,new Id("ip",""));

        List<ACL> acls = new ArrayList<ACL>();
        acls.add(acl);
        acls.add(acl1);
        zookeeper.create("/auth","123".getBytes(), acls,CreateMode.PERSISTENT);

    }
}
