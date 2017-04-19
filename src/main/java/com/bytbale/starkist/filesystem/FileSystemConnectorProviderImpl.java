package com.bytbale.starkist.filesystem;

import com.bytbale.starkist.model.Job;

import javax.annotation.Resource;

/**
 * Created by tomerlev on 19/01/2017.
 */
public class FileSystemConnectorProviderImpl implements FileSystemConnectorProvider {

    @Resource(name = "S3Connector")
    FileSystemConnector s3Connector;


    @Override
    public FileSystemConnector getBySource(Job.Source source) {
        FileSystemConnector connector = null;
        switch (source) {
            case HDFS:
                break;
            case S3:
                connector = s3Connector;
                break;
            case LOCAL_FS:
                break;

        }
        return connector;
    }
}
