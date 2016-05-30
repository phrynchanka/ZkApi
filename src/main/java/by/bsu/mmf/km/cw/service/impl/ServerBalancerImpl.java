package by.bsu.mmf.km.cw.service.impl;

import by.bsu.mmf.km.cw.service.ServerBalancer;
import by.bsu.mmf.km.cw.service.ServerProvider;
import org.apache.zookeeper.KeeperException;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Pavel Hrynchanka on 5/30/2016.
 */
public class ServerBalancerImpl implements ServerBalancer {
    ServerProvider serverProvider = new ServerProviderImpl();
    private Lock lock = new ReentrantLock();

    public ServerBalancerImpl() {
    }


    @Override
    public List<String> loadBalancerList(String api, String apiOldVersion, String category) throws KeeperException, InterruptedException {
        lock.lock();
        List<String> updList = serverProvider.getCurrentServerListByApiVersionCategory(api, apiOldVersion, category);
        lock.unlock();
        return updList;
    }

    @Override
    public void loadBalance(Long clientQuantity, List<String> listBalanceUpdater, Integer percentage, String apiNewVersion) {
        Long currentClientQuantity = (percentage / 100) * clientQuantity;
        lock.lock();
        for (int i = 0; i < currentClientQuantity; ++i) {
            //for client with necessary id update server his provided
        }
        lock.lock();
    }
}
