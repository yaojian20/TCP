package com.yao.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yaojian
 * @date 2020/7/23 23:13
 */
public class CuratorApi {


    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorUtil.getInstance();

        //创建子节点，如果父节点没有会一起创建父节点，永久性节点
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/node/node1","node".getBytes());

        Stat stat = new Stat();
        //获得节点数据
        byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/node");

        //设置数据
        Stat stat1 = curatorFramework.setData().forPath("/node","123".getBytes());



        //删除节点，如果有子节点会删除子节点再删除父节点
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/node");


        //异步操作


        final CountDownLatch countDownLatch = new CountDownLatch(1);
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).inBackground(new BackgroundCallback() {
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println(Thread.currentThread().getName() + "->result code is :" + event.getResultCode() + "type is:" + event.getType());
                countDownLatch.countDown();
            }
        },executorService).forPath("/yao/yao1","123".getBytes());


        countDownLatch.await();

        executorService.shutdown();


        //事务操作
        Collection<CuratorTransactionResult>  results =  curatorFramework.inTransaction().create().forPath("/111","1111".getBytes()).and().
                setData().forPath("/111","www".getBytes()).and().commit();

        for (CuratorTransactionResult result :results){
            System.out.println(result.getForPath() + "->" + result.getType());
        }
    }
}
