package by.bsu.mmf.km.cw.util;

import java.util.List;

/**
 * Created by Pavel Hrynchanka on 5/8/2016.
 */
public interface WorkerAction {
    void register(String path, String data);

    void updateState(String path, String data);

    Object getData(String path);

    List<String> getChildren(String path);

    void delete(String path);
}
