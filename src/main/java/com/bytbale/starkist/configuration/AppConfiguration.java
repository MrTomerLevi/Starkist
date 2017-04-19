package com.bytbale.starkist.configuration;

import com.bytbale.starkist.filesystem.FileSystemConnector;
import com.bytbale.starkist.filesystem.FileSystemConnectorProvider;
import com.bytbale.starkist.filesystem.FileSystemConnectorProviderImpl;
import com.bytbale.starkist.filesystem.s3.S3Connector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tomerlev on 19/01/2017.
 */
@Configuration
public class AppConfiguration {

    @Bean
    public FileSystemConnectorProvider fileSystemConnector(){
        return new FileSystemConnectorProviderImpl();
    }

    @Bean(name="S3Connector")
    public FileSystemConnector s3Connector(){
        return new S3Connector();
    }
}
