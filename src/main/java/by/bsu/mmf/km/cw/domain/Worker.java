package by.bsu.mmf.km.cw.domain;

import by.bsu.mmf.km.cw.util.WorkerAction;
import by.bsu.mmf.km.cw.util.ZKResourceManager;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Pavel Hrynchanka on 5/8/2016.
 */
public class Worker implements Watcher, WorkerAction {
    private final static String PORT_PARAMETER = "port";
    private final static String SESSION_TIMEOUT_PARAMETER = "sessionTimeout";
    private final static String DEFAULT_DATA = "Idle";
    private final static int latchQuantity = 1;
    private static ZKResourceManager zkResourceManager = ZKResourceManager.getInstance();
    private CountDownLatch countDownLatch;
    private ZooKeeper zk;
    private String host;
    private int sessionTimeout;
    private boolean connected;
    private boolean expired;

    public Worker() {
        countDownLatch = new CountDownLatch(latchQuantity);
        this.host = zkResourceManager.getValue(PORT_PARAMETER);
        this.sessionTimeout = Integer.valueOf(zkResourceManager.getValue(SESSION_TIMEOUT_PARAMETER)).intValue();
        connected = false;
        expired = false;
    }


    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType().equals(Event.EventType.None)) {
            switch (watchedEvent.getState()) {
                case SyncConnected:
                    connected = true;
                    countDownLatch.countDown();
                    break;
                case Disconnected:
                    connected = false;
                    countDownLatch.countDown();
                    break;
                case Expired:
                    connected = false;
                    expired = true;
                    countDownLatch.countDown();
                default:
                    //logger
                    break;
            }
        }
    }

    @Override
    public void register(String path, String data) {
        try {
            zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, createWorkerCallback, null);
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateState(String path, String data) {
        zk.setData(path, data.getBytes(), -1, updateStatCallback, data);
    }

    private AsyncCallback.StatCallback updateStatCallback = new AsyncCallback.StatCallback() {
        @Override
        public void processResult(int i, String path, Object ctx, Stat stat) {
            if (KeeperException.Code.get(i).equals(KeeperException.Code.CONNECTIONLOSS)) {
                updateState(path, (String) ctx);
            }
        }
    };

    @Override
    public Object getData(String path) {
        return null;
    }

    private StringCallback createWorkerCallback = new StringCallback() {
        @Override
        public void processResult(int i, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(i)) {
                case CONNECTIONLOSS:
                    register(path, (String) ctx);
                    countDownLatch.countDown();
                    break;
                case OK:
                    break;
                case NODEEXISTS:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public List<String> getChildren(String path) {
        return null;
    }

    @Override
    public void delete(String path) {

    }

    public void connect() {
        try {
            zk = new ZooKeeper(host, sessionTimeout, this);
            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
            //explain
        }
    }

    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            //explain
        }
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.connect();
        worker.register("/api", "pangramia.com:2181");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

