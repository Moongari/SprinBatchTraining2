package com.moon.bankspringbatch.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BankRestController {

    @Autowired  private JobLauncher jobLauncher;
    @Autowired  private  Job job;

    public  BankRestController(JobLauncher jobLauncher, Job job){
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    @GetMapping("/startJob")
    public BatchStatus load() throws Exception{
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        JobParameters parameters = new JobParameters(jobParameterMap);
        jobParameterMap.put("time",new JobParameter(System.currentTimeMillis()));
        JobExecution jobExecution = jobLauncher.run(job,parameters);
        while (jobExecution.isRunning()){
            System.out.println("je suis en cours d'execution...");
        }
        return jobExecution.getStatus();
    }

}
