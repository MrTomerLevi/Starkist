package com.bytbale.starkist.controller;

import com.bytbale.starkist.scheduling.JobScheduler;
import com.bytbale.starkist.model.Job;
import com.bytbale.starkist.repository.JobRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by tomerlev on 16/01/2017.
 */
@RestController
public class ScheduleController {

    private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    JobScheduler jobScheduler;

    @Autowired
    JobRepository repository;

    @PostMapping(value = "/job")
    public Job postJob(@RequestBody Job job) throws SchedulerException {
        log.info("New job received: " + job);
        Job savedJob = repository.save(job);
        jobScheduler.schedule(savedJob);
        return savedJob;
    }

    @GetMapping("/jobs")
    public List<Job> listJobs() {
        log.info("List all jobs called...");
        return repository.findAll();
    }

    @GetMapping("/job")
    public Job getJob(@RequestParam(value="id") Integer id) {
        log.info("Find job by id called, id=" + id);
        return repository.findById(id).get();
    }

    @DeleteMapping("/delete")
    public void deleteById(@RequestParam(value="id") Integer id) {
        repository.delete(id);
    }

    @DeleteMapping("/deletebyname")
    public List<Job> deleteByName(@RequestParam(value="name") String name) {
        return repository.deleteByName(name);
    }

    @DeleteMapping("/purge")
    public void deleteAll() {
        repository.deleteAll();
    }

}
