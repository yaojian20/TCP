package com.yao.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author yaojian
 * @date 2021/12/12 22:39
 */
public class Test {

    /**
     * 使用CuratorCache监听需要将
     *          <dependency>
     *             <groupId>org.apache.zookeeper</groupId>
     *             <artifactId>zookeeper</artifactId>
     *             <version>3.6.3</version>
     *         </dependency>
     *         版本跟zk版本更新到3.6以上
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorUtil.getInstance();
        /**
         * 现在nodecache,childpathcache等不推荐使用，统一推荐下面此方法
         */
        Executor executor = Executors.newFixedThreadPool(1);
        CuratorCache curatorCache = CuratorCache.builder(curatorFramework, "/node").build();
        //nodecache
        CuratorCacheListener listener = CuratorCacheListener
                .builder()
                // 3 types of cache listener, choose one in case
                .forPathChildrenCache("/node", curatorFramework, (c, e) -> {
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
        CuratorCache cache = CuratorCache.builder(curatorFramework, "/node").build();
        System.out.println(("CuratorCache building complete ,listener path :{}"+ "/node"));
        // 缓存数据
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent)
                    throws Exception {
                System.out.println("事件路径:" + pathChildrenCacheEvent.getData().getPath() + "事件类型"
                        + pathChildrenCacheEvent.getType());
            }
        };
        CuratorCacheListener pathlistener = CuratorCacheListener.builder()
                .forPathChildrenCache("/", curatorFramework, pathChildrenCacheListener).build();
        cache.listenable().addListener(pathlistener);
        cache.start();
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/node","value1".getBytes());
        curatorFramework.setData().forPath("/node","value".getBytes());
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/node/zkClient","value1".getBytes());
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/node");
//
//        CuratorFramework client = curatorService.getCuratorClient();
//        CuratorFramework client = curatorService.getCuratorClient();
        // 开启会话

//        CuratorCache curatorCache = CuratorCache.builder(curatorFramework,"/node").build();
//        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
//                switch (pathChildrenCacheEvent.getType()){
//                    case INITIALIZED:
//                        System.out.println("初始化===================");
//                        break;
//                    case CHILD_ADDED:
//                        System.out.println("增加子节点===============" + pathChildrenCacheEvent.getData());
//                        break;
//                    case CHILD_REMOVED:
//                        System.out.println("正在移除子节点===============");
//                        break;
//                    case CHILD_UPDATED:
//                        System.out.println("正在更新子节点================");
//                }
//            }
//        };
//        CuratorCacheListener cacheListener = CuratorCacheListener.builder().forPathChildrenCache("/node",curatorFramework,listener).build();
//        curatorCache.listenable().addListener(cacheListener);
//        curatorCache.listenable().addListener(new CuratorCacheListener() {
//            @Override
//            public void event(Type type, ChildData oldData, ChildData data) {
//                switch (type){
//                    case NODE_CREATED:
//                        System.out.println("创建节点！！！！！！！！！！！！！！！！oldData is :" + oldData + "newData is :" + data);
//                        break;
//                    case NODE_CHANGED:
//                        System.out.println("更新节点！！！！！！！！！！！！！！！！oldData is :" + oldData + "newData is :" + data);
//                        break;
//                    case NODE_DELETED:
//                        System.out.println("删除节点！！！！！！！！！！！！！！！！oldData is :" + oldData + "newData is :" + data);
//                        break;
//                }
//                System.out.println("==================================================");
//            }
//        });
//        curatorCache.start();
//        curatorFramework.create().creatingParentsIfNeeded().forPath("/node","value".getBytes());
//        curatorFramework.setData().forPath("/node","value2".getBytes());
//        curatorFramework.create().creatingParentsIfNeeded().forPath("/node/child","123".getBytes());
//        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/node");

        System.in.read();
    }
}
