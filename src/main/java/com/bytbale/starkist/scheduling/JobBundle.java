package com.bytbale.starkist.scheduling;

import com.bytbale.starkist.filesystem.FileSystemConnector;
import com.bytbale.starkist.filesystem.FileSystemConnectorProvider;
import com.bytbale.starkist.filesystem.utils.ZipUtils;
import com.bytbale.starkist.model.Job;
import org.apache.commons.io.IOUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by tomerlev on 17/01/2017.
 */
public class JobBundle implements org.quartz.Job {

    private static final Logger log = LoggerFactory.getLogger(JobBundle.class);

    FileSystemConnectorProvider fileSystemConnectorProvider;

    String tempFilesDownloadPath;

    public JobBundle() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String file = jobDataMap.getString("file");
        String name = jobDataMap.getString("name");
        Integer id = jobDataMap.getIntValue("id");
        Job.Source source = Job.Source.valueOf(jobDataMap.getString("source"));

        fileSystemConnectorProvider = (FileSystemConnectorProvider)jobDataMap.get("fscp");
        tempFilesDownloadPath = jobDataMap.getString("tempFilesDownloadPath");

        log.info("Processing job name:{}, id: {}, file: {}, source: {}", name,id,file,source.name());
        downloadFile(file,source);
        unzipFile(file);
        executeScript(tempFilesDownloadPath + "/" + file.substring(0,file.indexOf('.')));
    }

    private void downloadFile(String file, Job.Source source) throws JobExecutionException {
        try {
            log.info("Starting to download file: {} from: {}", file,source.name());
            FileSystemConnector fileSystemConnector = fileSystemConnectorProvider.getBySource(source);
            fileSystemConnector.download(file, tempFilesDownloadPath);
            log.info("Finished to download file: {} from: {} into: {}", file,source.name(),tempFilesDownloadPath);
        } catch (IOException e) {
            throw new JobExecutionException("Job failed. Unable to download job file (source/file):" + source.name() + "/" + file,e);
        }
    }

    private void unzipFile(String file) throws JobExecutionException {
        try {
            log.info("Starting to unzip file: {} into: {}", tempFilesDownloadPath + "/" + file,tempFilesDownloadPath);
            ZipUtils.unzip(tempFilesDownloadPath + "/" + file, tempFilesDownloadPath);
            log.info("Finished to unzip file: {}  into: {}", file,tempFilesDownloadPath);

        } catch (IOException e) {
            throw new JobExecutionException("Job failed. Unable to unzip job file (source/destination)" + file + "/" + tempFilesDownloadPath,e);
        }
    }

    private boolean executeScript(String folder) throws JobExecutionException {
        try {
            boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
            Process process;
            if (isWindows) {
                process = Runtime.getRuntime().exec(String.format("%s/start.bat", folder));
            } else {
                File startScript = new File(folder,"start.sh");
                startScript.setExecutable(true);
                process = Runtime.getRuntime().exec(String.format("/bin/sh %s/start.sh", folder));
            }

            // 0 means success
            int exitStatus = process.waitFor();
            if(exitStatus == 0){
                log.info("Script execution from folder: {} has finished successfully", folder);
            } else {
                String errorMessage = getErrorMessage(process);
                log.error("Script execution from folder: {} failed. {}",folder,errorMessage);
            }

            return exitStatus == 0;
        } catch (Exception e) {
            throw new JobExecutionException("Job failed. Unable to execute start script in: " + folder,e);
        }

    }

    private String getErrorMessage(Process process) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> strings = IOUtils.readLines(process.getErrorStream());
        strings.forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}

