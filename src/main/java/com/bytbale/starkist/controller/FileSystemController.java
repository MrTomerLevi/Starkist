package com.bytbale.starkist.controller;

import com.bytbale.starkist.model.Job;
import com.bytbale.starkist.filesystem.FileSystemConnectorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FileSystemController {

    @Autowired
    private FileSystemConnectorProvider connectorProvider;

    @Value("${file.download.path}")
    private String downloadPath;

    @GetMapping(value = "/download")
    public void download(@RequestParam String key) throws IOException {
         connectorProvider.getBySource(Job.Source.S3).download(key,downloadPath);
    }

}