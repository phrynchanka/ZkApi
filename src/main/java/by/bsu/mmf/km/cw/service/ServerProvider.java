package by.bsu.mmf.km.cw.service;

import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * Created by Pavel Hrynchanka on 5/16/2016.
 */
public interface ServerProvider {

    List<String> getCurrentServerListByVersion(String version) throws KeeperException, InterruptedException;

    List<String> getCurrentServerListByApiVersion(String api, String version) throws KeeperException, InterruptedException;

    List<String> getCurrentServerListByApiVersionCategory(String api, String version, String category) throws KeeperException, InterruptedException;
}
