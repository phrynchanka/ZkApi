package by.bsu.mmf.km.cw.service;

import java.util.List;

/**
 * Created by Pavel Hrynchanka on 5/8/2016.
 */
public interface ServerBalancer {
    List<String> loadBalancerList(String branch, String version, String region, Integer persentage);

    void loadBalance(Long clientId, List<String> listBalanceUpdater);
}
