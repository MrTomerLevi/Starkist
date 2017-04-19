package com.bytbale.starkist.scheduling;

import com.bytbale.starkist.filesystem.FileSystemConnectorProvider;
import com.bytbale.starkist.model.Job;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by tomerlev on 17/01/2017.
 */
@Component
public class JobSchedulerImpl implements JobScheduler {

    private static final Logger log = LoggerFactory.getLogger(JobSchedulerImpl.class);

    @Autowired
    FileSystemConnectorProvider fileSystemConnectorProvider;

    @Value("${file.download.path}")
    private String downloadPath;

    Scheduler scheduler;


    public JobSchedulerImpl() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();
        scheduler.start();
    }

    @Override
    public void schedule(Job job) throws SchedulerException {
        log.info("Scheduling new job: " + job);
        JobDataMap jdm = new JobDataMap();
        jdm.put("file",job.getFile());
        jdm.put("name",job.getName());
        jdm.put("id",job.getId());
        jdm.put("tempFilesDownloadPath",downloadPath);
        jdm.put("source",job.getSource().name());
        jdm.put("fscp",fileSystemConnectorProvider);

        JobDetail jobToTrigger = newJob(JobBundle.class)
                .withIdentity(job.getId().toString())
                .setJobData(jdm)
                .build();

        CronTrigger trigger = newTrigger()
                .withSchedule(cronSchedule(job.getCron()))
                .build();

        scheduler.scheduleJob(jobToTrigger, trigger);
    }

    @Override
    public void remove(Integer id) throws SchedulerException {
        scheduler.deleteJob(new JobKey(id.toString()));
    }

    @Override
    public void removeAll() {
        //TODO
    }

}
