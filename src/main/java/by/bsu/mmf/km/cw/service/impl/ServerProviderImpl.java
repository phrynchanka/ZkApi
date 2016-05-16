package by.bsu.mmf.km.cw.service.impl;

import by.bsu.mmf.km.cw.service.ServerProvider;

import java.util.List;

/**
 * Created by Pavel Hrynchanka on 5/16/2016.
 */
public class ServerProviderImpl implements ServerProvider {

    @Override
    public List<String> getCurrentServerListByVersion(String version) {
        return null;
    }

    @Override
    public List<String> getCurrentServerListByApiVersion(String api, String version) {
        return null;
    }

    @Override
    public List<String> getCurrentServerListByApiVersionRegion(String api, String version, String region) {
        return null;
    }
}
