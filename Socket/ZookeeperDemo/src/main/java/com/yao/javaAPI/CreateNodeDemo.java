package com.yao.javaAPI;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.proto.WatcherEvent;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author yaojian
 * @date 2020/7/21 9:39
 */
public class CreateNodeDemo implements Watcher {

    private final static String CONNECTTIONURL = "192.168.88.88:2181,192.168.88.89:2181" +
            "192.168.88.90:2181,192.168.88.91:2181";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zookeeper;

    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        zookeeper = new ZooKeeper(CONNECTTIONURL,5000,new CreateNodeDemo());

        countDownLatch.await();//用来阻塞线程，因为zookeeper是异步的线程，等待zookeeper创建链接之后主线程再继续

        System.out.println(zookeeper.getState());
        String result = zookeeper.create("/node1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zookeeper.getData("/node1", new CreateNodeDemo(), stat);//添加监听

        //修改数据
        zookeeper.setData("/node1", "node123".getBytes(), -1);//-1是不管版本号
        Thread.sleep(2000);
        System.out.println(result);
        zookeeper.setData("/node1","345".getBytes(),-1);
        Thread.sleep(2000);
        //删除node
        zookeeper.delete("/del/del1",-1);
        Thread.sleep(2000);

        //创建子节点
        String path = "/nodes2";
        Stat st = zookeeper.exists(path,true);
        if(st==null){
            //节点不存在
            zookeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            TimeUnit.SECONDS.sleep(1);
        }
        Stat st1 = zookeeper.exists(path+"/node1",true);
        if(st1==null){
            //节点不存在
            zookeeper.create(path+"/node1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            TimeUnit.SECONDS.sleep(1);
        }
        TimeUnit.SECONDS.sleep(1);

        //修改子路径
        zookeeper.setData(path+"/node1","mic123".getBytes(),-1);
        TimeUnit.SECONDS.sleep(1);
        //获取子节点
        List<String> children = zookeeper.getChildren("node/",true);


    }


    //zookeeper监听事件
    public void process(WatchedEvent event) {
        //CountDownLatch可以实现多线程之间的计数器，并实现阻塞功能。
        if (event.getState() == Event.KeeperState.SyncConnected){
            if (Event.EventType.None == event.getType() && event.getPath() == null){
                //计数器-1
                countDownLatch.countDown();
                System.out.println(event.getType() + "-->" + event.getPath());
            } else if(Event.EventType.NodeChildrenChanged == event.getType()){
                //子节点发生改变
                try {
                    System.out.println("子节点发生改变，path:" + event.getPath() + ",data :" + zookeeper.getData(event.getPath(), true, stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if(Event.EventType.NodeDataChanged == event.getType()){
                //节点data发生改变
                try {
                    System.out.println("节点数据发生改变，path:" + event.getPath() + ",data :" + zookeeper.getData(event.getPath(), true, stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if(Event.EventType.NodeDeleted == event.getType()){
                //节点被删除
                try {
                    System.out.println("节点被删除，path:" + event.getPath() + ",data :" + zookeeper.getData(event.getPath(), true, stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if(Event.EventType.NodeCreated == event.getType()){
                //创建节点
                try {
                    System.out.println("创建节点，path:" + event.getPath() + ",data :" + zookeeper.getData(event.getPath(), true, stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
