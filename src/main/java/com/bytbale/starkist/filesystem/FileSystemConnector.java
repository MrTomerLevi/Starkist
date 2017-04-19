package com.bytbale.starkist.filesystem;

import java.io.IOException;

/**
 * Created by tomerlev on 19/01/2017.
 */
public interface FileSystemConnector {

    void download(String key, String downloadPath) throws IOException;
}
