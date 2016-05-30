package by.bsu.mmf.km.cw.service;

import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * Created by Pavel Hrynchanka on 5/8/2016.
 */
public interface ServerBalancer {
    /**
     * @param api
     * @param apiOldVersion
     * @param category
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    List<String> loadBalancerList(String api, String apiOldVersion, String category) throws KeeperException, InterruptedException;

    /**
     * @param clientQuantity
     * @param listBalanceUpdater
     * @param percentage
     * @param apiNewVersion
     */
    void loadBalance(Long clientQuantity, List<String> listBalanceUpdater, Integer percentage, String apiNewVersion);
}
