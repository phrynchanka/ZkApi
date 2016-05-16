package by.bsu.mmf.km.cw.util;

import java.util.ResourceBundle;

/**
 * Created by Pavel Hrynchanka on 5/11/2016.
 */
public class ZKResourceManager {
    private final static String PROPERTY_FILE_NAME = "zk";
    private final static ZKResourceManager zkResourceManager = new ZKResourceManager();
    private ResourceBundle resourceBundle;

    public ZKResourceManager() {
        resourceBundle = ResourceBundle.getBundle(PROPERTY_FILE_NAME);
    }

    public String getValue(String key) {
        return resourceBundle.getString(key);
    }

    public static ZKResourceManager getInstance() {
        return zkResourceManager;
    }
}
