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
    public List<String> getCurrentServerListByBranchVersion(String branch, String version) {
        return null;
    }

    @Override
    public List<String> getCurrentServerListByBranchVersionCategory(String branch, String version, String category) {
        return null;
    }
}
