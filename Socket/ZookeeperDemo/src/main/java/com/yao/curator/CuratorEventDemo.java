package com.yao.curator;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yaojian
 * @date 2020/7/23 23:37
 */
public class CuratorEventDemo {
    /**
     * 三种watcher来做节点的监听
     * PathChildrenCache 监视一个路径下子节点的创建、删除、节点数据更让新
     * NodeCache 监视一个节点的创建更新删除
     * TreeCache pathcache+nodecache合体
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws Exception {

        CuratorFramework curatorFramework = CuratorUtil.getInstance();

        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/zkClient");
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/zkClient1");

        //监听路径和是否压缩
        NodeCache nodeCache = new NodeCache(curatorFramework,"/zkClient", false);

        try {
            //启动监听
            nodeCache.start(true);

            nodeCache.getListenable().addListener(new NodeCacheListener() {
                public void nodeChanged() throws Exception {
                    System.out.println(nodeCache.getPath());
                    System.out.println("更新后的值为：" + new String(nodeCache.getCurrentData().getData()));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/zkClient","value1".getBytes());
            curatorFramework.setData().forPath("/zkClient", "value2".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,"/zkClient",true);

        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()){
                    case CHILD_ADDED:
                        System.out.println("正在添加子节点：" + event.getData());
                    case CHILD_REMOVED:
                        System.out.println("正在移除子节点：" + event.getData());
                }
            }
        });
        pathChildrenCache.start();
        curatorFramework.create().creatingParentsIfNeeded().forPath("/zkClient/client","test".getBytes());

        /**
         * 现在nodecache,childpathcache等不推荐使用，统一推荐下面此方法
         */
        Executor executor = Executors.newFixedThreadPool(1);
        CuratorCache curatorCache = CuratorCache.builder(curatorFramework, "/zkClient").build();
        //nodecache
        CuratorCacheListener listener = CuratorCacheListener
                .builder()
                // 3 types of cache listener, choose one in case
                .forPathChildrenCache("/zkClient", curatorFramework, (c, e) -> {
                    System.out.println("la");
                    // child node listener
                    switch (e.getType()) {
                        default:
                            System.out.println("1111111111111111111111");
                            break;
                    }
                })
                .forNodeCache(() -> {
                    System.out.println("hello world!");
                })
                .forTreeCache(curatorFramework, (c, e) -> {
                    System.out.println(("status changed:{}"+e.getType()));
                    switch (e.getType()) {
                        case INITIALIZED:
                            System.out.println("init");
                            break;
                        case NODE_ADDED:
                            System.out.println("node add");
                            break;
                        case NODE_UPDATED:
                            System.out.println("node update");
                            break;
                        case NODE_REMOVED:
                            System.out.println("node remove");
                            break;
                        default:
                            break;
                    }
                })
                .build();
        curatorCache.listenable().addListener(listener);
        curatorCache.start();
        CuratorCache cache = CuratorCache.builder(curatorFramework, "/zkClient").build();
        System.out.println(("CuratorCache building complete ,listener path :{}"+ "/zkClient"));
        PathChildrenCacheListener listener1 = new PathChildrenCacheListener()
        {
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception
            {
                switch ( event.getType() )
                {
                    case CHILD_ADDED:
                    {
                        System.out.println("Node added: " + event.getData().getPath());
                        break;
                    }

                    case CHILD_UPDATED:
                    {
                        System.out.println("Node changed: " + event.getData().getPath());
                        break;
                    }

                    case CHILD_REMOVED:
                    {
                        System.out.println("Node removed: " + event.getData().getPath());
                        break;
                    }
                }
            }
        };
        cache.listenable().addListener(listener);
        cache.start();
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/zkClient1","value1".getBytes());
        curatorFramework.setData().forPath("/zkClient1","value".getBytes());
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/zkClient1/zkClient","value1".getBytes());
        System.in.read();

    }
}
