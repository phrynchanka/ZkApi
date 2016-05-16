package by.bsu.mmf.km.cw.domain;

import by.bsu.mmf.km.cw.util.WorkerAction;
import by.bsu.mmf.km.cw.util.ZKResourceManager;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.*;

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
    private String path;
    private boolean connected;
    private boolean expired;

    public Worker() {
        countDownLatch = new CountDownLatch(latchQuantity);
        this.host = zkResourceManager.getValue(PORT_PARAMETER);
        this.sessionTimeout = Integer.valueOf(zkResourceManager.getValue(SESSION_TIMEOUT_PARAMETER)).intValue();
        connected = false;
        expired = false;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
    public void register() {
        try {
            zk.create(path, DEFAULT_DATA.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, createWorkerCallback, null);
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private StringCallback createWorkerCallback = new StringCallback() {
        @Override
        public void processResult(int i, String s, Object o, String s1) {
            switch (KeeperException.Code.get(i)) {
                case CONNECTIONLOSS:
                    register();
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
    public void update(String part, byte[] data) {

    }

    @Override
    public Object setData(String path, boolean watchFlag) {
        return null;
    }

    @Override
    public Object getData(String path, boolean watchFlag) {
        return null;
    }

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
        worker.setPath("/api");
        worker.register();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

