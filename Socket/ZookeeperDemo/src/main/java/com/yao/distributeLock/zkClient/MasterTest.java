package com.yao.distributeLock.zkClient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author yaojian
 * @date 2020/7/28 16:19
 */
public class MasterTest {

    private final static String CONNECTTIONURL = "192.168.88.88:21810,192.168.88.89:21810," +
            "192.168.88.90:21810,192.168.88.91:21810";

    private  static ZkClient getInstance(){
        return  new ZkClient(CONNECTTIONURL,100000,100000,new SerializableSerializer());



    }


    public static void main(String[] args) {
        ZkClient zkClient = getInstance();
        ExecutorService service = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(10);
        for (int i = 0; i < 10; i++) {
            final int idx = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        //定义一台争抢master节点的服务器
                        UserCenter serverData = new UserCenter();
                        serverData.setCenter_id(""+idx);
                        serverData.setCenter_name("#server-" + idx);
                        //初始化一个争抢master节点的服务
                        MasterSelector ms = new MasterSelector(zkClient, serverData);
                        System.out.println(serverData.getCenter_name() + "开始争抢master");
                        ms.start();
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        service.shutdown();
    }
}
