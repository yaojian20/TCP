package com.yao.distributeLock.javvaAPILock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author yaojian
 * @date 2020/7/25 22:01
 */
public class ZookeeperLock {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);


    private static  final String ROOT_LOCKS = "/LOCKS";

    private static  final byte[] data = {1,2};

    private ZooKeeper zooKeeper;

    private int sessionTimeout;

    private String lockId;

    public ZookeeperLock() throws IOException, InterruptedException {
        zooKeeper = ZookeeperClient.getInstance();
        sessionTimeout = ZookeeperClient.sessionTimeout;
    }

    public synchronized boolean lock(){
        try {
            //创建临时有序节点，格式例如/LOCKS/0000000001
            lockId = zooKeeper.create(ROOT_LOCKS+"/",data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName()+"成功创建了节点：" + lockId);
            List<String> childrenNodes = zooKeeper.getChildren(ROOT_LOCKS,true);
            SortedSet<String> childrens = new TreeSet<String>();
            for (String children: childrenNodes){
                //按从小到大排序
                childrens.add(ROOT_LOCKS + "/" + children);
            }

            String firstNode = childrens.first();//拿到最小的节点
            if (lockId.equals(firstNode)){
                //如果相等则说明我当前就是最小的，意味着成功获取到额锁，成功获得了锁
                System.out.println(Thread.currentThread().getName()+"成功获得了锁，节点：" + lockId);

                return  true;
            }
            //找到比他小的节点集合
            SortedSet<String> lessThanChildren = childrens.headSet(lockId);

            String preNode = lessThanChildren.last();
            if (preNode != null && preNode != ""){
                //创建监听
                zooKeeper.exists(preNode, new LockWatcher(countDownLatch));

                //如果监听到上个节点被删除(锁释放)或者会话曹氏，则成功拿到锁
                countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);

                System.out.println(Thread.currentThread().getName()+"成功获得了锁，节点：" + lockId);

                return  true;
            }


        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  false;
    }


    public synchronized boolean unlock(){
        System.out.println(Thread.currentThread().getName()+"开始释放锁，节点：" + lockId);

        try {
            zooKeeper.delete(lockId,-1);
            System.out.println(Thread.currentThread().getName()+"释放锁成功，节点：" + lockId);

            return  true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

        return  false;
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0;i <10 ; i++){
            new Thread(()->{
                ZookeeperLock zookeeperLock = null;
                try {
                     zookeeperLock = new ZookeeperLock();
                     zookeeperLock.lock();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (zookeeperLock != null){
                        zookeeperLock.unlock();
                    }
                }


            }).start();
        }
    }







}
