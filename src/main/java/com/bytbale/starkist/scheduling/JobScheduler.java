package com.bytbale.starkist.scheduling;

import com.bytbale.starkist.model.Job;
import org.quartz.SchedulerException;

/**
 * Created by tomerlev on 17/01/2017.
 */
public interface JobScheduler {

    void schedule(Job job) throws SchedulerException;

    void remove(Integer id) throws SchedulerException;

    void removeAll();
}
