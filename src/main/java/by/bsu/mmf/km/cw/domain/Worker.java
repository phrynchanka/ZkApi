package by.bsu.mmf.km.cw.domain;

import by.bsu.mmf.km.cw.util.ZKResourceManager;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Pavel Hrynchanka on 5/8/2016.
 */
public class Worker implements Watcher {
    private final static String PORT_PARAMETER = "port";
    private final static String SESSION_TIMEOUT_PARAMETER = "sessionTimeout";
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

    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            //logger
        }
    }

}