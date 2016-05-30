package by.bsu.mmf.km.cw.service.impl;

import by.bsu.mmf.km.cw.service.ServerAction;
import by.bsu.mmf.km.cw.service.ServerProvider;
import org.apache.zookeeper.KeeperException;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Pavel Hrynchanka on 5/30/2016.
 */
public class ServerProviderImpl implements ServerProvider {
    private final static String API = "/api-";
    private final static String VERSION = "/v";
    private final static String CATEGORY = "/category";

    private ServerAction serverAction = new ServerActionImpl();
    private Lock lock = new ReentrantLock();

    public ServerProviderImpl() {
    }


    @Override
    public List<String> getCurrentServerListByVersion(String version) throws KeeperException, InterruptedException {
        String path = API + version;
        return getChildrenByPath(path);
    }

    @Override
    public List<String> getCurrentServerListByApiVersion(String api, String version) throws KeeperException, InterruptedException {
        String path = API + api + VERSION + version;
        return getChildrenByPath(path);
    }

    @Override
    public List<String> getCurrentServerListByApiVersionCategory(String api, String version, String category) throws KeeperException, InterruptedException {
        String path = API + api + VERSION + version + CATEGORY + category;
        return getChildrenByPath(path);
    }

    private List<String> getChildrenByPath(String path) throws KeeperException, InterruptedException {
        lock.lock();
        List<String> serverList = serverAction.getChildren(path);
        lock.unlock();
        return serverList;
    }
}
