package by.bsu.mmf.km.cw.service;

import java.util.List;

/**
 * Created by Pavel Hrynchanka on 5/16/2016.
 */
public interface ServerProvider {

    List<String> getCurrentServerListByVersion(String version);

    List<String> getCurrentServerListByApiVersion(String api, String version);

    List<String> getCurrentServerListByApiVersionRegion(String api, String version, String region);
}
