package com.bytbale.starkist.filesystem.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.bytbale.starkist.filesystem.FileSystemConnector;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* Base on: https://github.com/brant-hwang/spring-cloud-aws-example */

public class S3Connector implements FileSystemConnector {

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public void download(String fileName, String downloadPath) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, fileName);
        S3Object s3Object = amazonS3Client.getObject(getObjectRequest);
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(objectInputStream);
        OutputStream outputStream = new FileOutputStream(downloadPath + "/" + fileName);
        IOUtils.write(bytes,outputStream);
        outputStream.close();
    }

}