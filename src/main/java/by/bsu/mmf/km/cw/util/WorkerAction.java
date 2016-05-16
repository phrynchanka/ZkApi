package by.bsu.mmf.km.cw.util;

import java.util.List;

/**
 * Created by Pavel Hrynchanka on 5/8/2016.
 */
public interface WorkerAction {
    void register();

    void update(String part, byte[] data);

    Object setData(String path, boolean watchFlag);

    Object getData(String path, boolean watchFlag);

    List<String> getChildren(String path);

    void delete(String path);
}
