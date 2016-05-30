package by.bsu.mmf.km.cw.service.impl;

import by.bsu.mmf.km.cw.domain.Worker;
import by.bsu.mmf.km.cw.service.ServerAction;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Pavel Hrynchanka on 5/30/2016.
 */
public class ServerActionImpl implements ServerAction {
    private Worker worker;
    private Lock lock = new ReentrantLock();

    public ServerActionImpl() {
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public String register(String path, String data) throws KeeperException, InterruptedException {
        lock.lock();
        String newPath = worker.getZk().create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        lock.unlock();
        return newPath;
    }

    @Override
    public Stat updateState(String path, String data) throws KeeperException, InterruptedException {
        lock.lock();
        Stat stat = worker.getZk().setData(path, data.getBytes(), -1);
        lock.unlock();
        return stat;
    }

    @Override
    public byte[] getData(String path) throws KeeperException, InterruptedException {
        lock.lock();
        byte[] data = worker.getZk().getData(path, worker, null);
        lock.unlock();
        return data;
    }

    @Override
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        lock.lock();
        List<String> children = worker.getZk().getChildren(path, true);
        lock.unlock();
        return children;
    }

    @Override
    public void delete(String path) throws KeeperException, InterruptedException {
        lock.lock();
        worker.getZk().delete(path, -1);
        lock.unlock();
    }
}
