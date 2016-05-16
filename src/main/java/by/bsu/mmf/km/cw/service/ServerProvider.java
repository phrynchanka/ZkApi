package by.bsu.mmf.km.cw.service;

import java.util.List;

/**
 * Created by Pavel Hrynchanka on 5/16/2016.
 */
public interface ServerProvider {

    List<String> getCurrentServerListByVersion(String version);

    List<String> getCurrentServerListByBranchVersion(String branch, String version);

    List<String> getCurrentServerListByBranchVersionCategory(String branch, String version, String category);
}
