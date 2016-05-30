package by.bsu.mmf.km.cw.service;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Created by Pavel Hrynchanka on 5/8/2016.
 */
public interface ServerAction {
    String register(String path, String data) throws KeeperException, InterruptedException;

    Stat updateState(String path, String data) throws KeeperException, InterruptedException ;

    byte[] getData(String path) throws KeeperException, InterruptedException;

    List<String> getChildren(String path) throws KeeperException, InterruptedException;

    void delete(String path) throws KeeperException, InterruptedException ;
}
