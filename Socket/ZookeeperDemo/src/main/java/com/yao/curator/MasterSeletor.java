package com.yao.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

/**
 * @author yaojian
 * @date 2021/12/15 20:08
 */
public class MasterSeletor {

    //curator自带leader选举

    public static void main(String[] args) {
        CuratorFramework curatorFramework = CuratorUtil.getInstance();
        LeaderSelector leaderSelector = new LeaderSelector(curatorFramework, "/curator_master", new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("获得leader成功！");
            }
        });
        leaderSelector.autoRequeue();
        leaderSelector.start();
    }
}
