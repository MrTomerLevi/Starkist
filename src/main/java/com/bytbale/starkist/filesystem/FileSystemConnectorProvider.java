package com.bytbale.starkist.filesystem;

import com.bytbale.starkist.model.Job;

/**
 * Created by tomerlev on 19/01/2017.
 */
public interface FileSystemConnectorProvider {

    FileSystemConnector getBySource(Job.Source source);
}
