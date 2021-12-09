package com.yao.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yaojian
 * @date 2020/7/21 22:28
 */


public class CreateSesssion {

    private final static String CONNECTTIONURL = "192.168.88.88:21810,192.168.88.89:21810" +
            ",192.168.88.90:21810,192.168.88.91:21810";

    private  static ZkClient getInstance(){
        return  new ZkClient(CONNECTTIONURL,5000);


    }

    public static void main(String[] args) throws InterruptedException {

        ZkClient zkClient = getInstance();
        //zkclient的特性，提供递归创建子节点
        zkClient.createPersistent("/zkclient/test",true);

        //递归删除
        zkClient.deleteRecursive("/zkclient/");

        //获得子节点
        List<String> children = zkClient.getChildren("/node");

        //改变数据监听
        zkClient.subscribeDataChanges("/node", new IZkDataListener() {
            public void handleDataChange(String dataPath, Object data) throws Exception {

                System.out.println("path:" + dataPath + "改变后的value：" + data);
            }

            public void handleDataDeleted(String dataPath) throws Exception {

            }
        });
        //修改data
        zkClient.writeData("/node","111");
        TimeUnit.SECONDS.sleep(2);
    }
}
