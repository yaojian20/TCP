package com.yao.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;

/**
 * @author yaojian
 * @date 2020/7/23 23:37
 */
public class CuratorEventDemo {

    public static void main(String[] args) throws IOException {

        CuratorFramework curatorFramework = CuratorUtil.getInstance();

        //监听路径和是否压缩
        final NodeCache nodeCache = new NodeCache(curatorFramework,"/yao", false);

        try {
            //启动监听
            nodeCache.start(true);

            nodeCache.getListenable().addListener(new NodeCacheListener() {
                public void nodeChanged() throws Exception {
                    System.out.println(nodeCache.getCurrentData().getData());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/yao","qqq".getBytes());
            curatorFramework.setData().forPath("/yao", "lalala".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.in.read();

        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,"/node",true);

        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {

            }
        });


    }
}
