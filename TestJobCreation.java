package com.quartzui;

import nitinka.quartzrest.core.SchedulerHelper;
import nitinka.quartzrest.domain.JobInfo;
import nitinka.quartzrest.domain.SimpleSchedulerInfo;
import nitinka.quartzrest.domain.TriggerInfo;
import nitinka.quartzrest.exception.JobAlreadyExistsException;
import nitinka.quartzrest.util.JobHelper;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.HashMap;
import java.util.List;

public class TestJobCreation {
  
    public static void main(String[] args) throws ClassNotFoundException, JobAlreadyExistsException, SchedulerException, InterruptedException {

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        SchedulerHelper schedulerHelper = new SchedulerHelper(scheduler);
        schedulerHelper.removeJob(new JobKey("Name1","Group1"));
        schedulerHelper.start();
        JobInfo jobInfo = new JobInfo().
                setJobClass("nitinka.quartzrest.job.AddJob").
                setJobParams(new HashMap<String, Object>()).
                setDescription("Sample Add Job").
                setName("Name1").
                setGroup("Group1").
                setStoreDurably(true);

        SimpleSchedulerInfo schedulerInfo = new SimpleSchedulerInfo().
                setInterval(30000).
                setRepeatCount(5).
                setRepeatForever(false);


        TriggerInfo triggerInfo = new TriggerInfo().
                setName("Name1").
                setGroup("Group1");

        schedulerHelper.scheduleJob(JobHelper.buildJobDetail(jobInfo),
                JobHelper.buildTrigger(triggerInfo,
                        JobHelper.buildSimpleSchedulerBuilder(schedulerInfo)));


        System.out.println("****************"+ schedulerHelper.listAllJobs()+"********************");
        Thread.sleep(1000000);


    }
}
