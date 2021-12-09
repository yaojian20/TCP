package com.yao.distributeLock.zkClient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.ZooDefs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author yaojian
 * @date 2020/7/28 16:17
 */
public class MasterSelector {


    //原理是利用zookeeper创建临时节点，临时节点在master宕机之后（会话结束后）会自动删除临时节点，这个时候通过监听，其他机器酒水进行master竞选

    //zookeeper服务器
    private ZkClient zkClient;

    //要进行master选举的用户中心
    private UserCenter userCenter;

    //记录master
    private UserCenter masterCenter;

    private static final String MASTER_PATH = "/master";

    private boolean isRunning = false;

    private MasterSeletorWatcher masterSeletorWatcher = new MasterSeletorWatcher();

    //定时调度
    private ScheduledExecutorService scheService = Executors.newScheduledThreadPool(1);

    /**
     * @param zkClient
     * @param userCenter
     */
    public MasterSelector(ZkClient zkClient, UserCenter userCenter) {
        super();
        this.zkClient = zkClient;
        this.userCenter = userCenter;
    }

    public void start() {
        if (!isRunning){
            isRunning = true;
            //注册监听
            zkClient.subscribeDataChanges(MASTER_PATH, masterSeletorWatcher);
            chooseMaster();
        }

    }

    public void stop() {
        if (isRunning){
            isRunning = false;
            //取消监听
            zkClient.unsubscribeDataChanges(MASTER_PATH, masterSeletorWatcher);
            //释放master，即删除master节点
            releaseMaster();
        }

    }

    private void chooseMaster(){

        //创建临时节点

        try {
            zkClient.createEphemeral(MASTER_PATH, userCenter, ZooDefs.Ids.OPEN_ACL_UNSAFE);
            masterCenter = userCenter;

            try {
                TimeUnit.SECONDS.sleep(5);
                releaseMaster();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (ZkNodeExistsException e) {
            //如果抛异常，说明该节点已经存在,即master已经存在
            try {
                masterCenter = zkClient.readData(MASTER_PATH, true);
                if (masterCenter != null){
                    System.out.println("master节点已经存在，master name is：" + masterCenter.getCenter_name());
                }

            } catch (ZkNodeExistsException e2) {
                // TODO: handle exception
                chooseMaster();
            }
        }



    }

    private boolean isMaster(){
        UserCenter masterUserCenter = zkClient.readData(MASTER_PATH);
        if (masterUserCenter.getCenter_name().equals(userCenter.getCenter_name())){
            return true;
        }

        return false;
    }


    //释放master
    private void releaseMaster(){
        //必须是master才能释放
        if (isMaster()){
            zkClient.delete(MASTER_PATH, -1);
        }

    }



    private class MasterSeletorWatcher implements IZkDataListener {

        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {

        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
            //监听节点删除，如果节点删除，就立刻竞选master
            scheService.schedule(new Runnable() {

                @Override
                public void run() {
                    //每5秒钟抢一次master
                    chooseMaster();
                }
            }, 5, TimeUnit.SECONDS);

        }
    }



}
