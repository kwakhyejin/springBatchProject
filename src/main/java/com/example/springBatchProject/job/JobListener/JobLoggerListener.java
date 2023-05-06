package com.example.springBatchProject.job.JobListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

// 다른 job에서도 재사용가능함.
@Slf4j
public class JobLoggerListener implements JobExecutionListener {


    private static String BEFORE_MESSAGE = "{} Job is Running!!!"; // job의 이름을 작성하기 위해 {}

    private static String AFTER_MESSAGE = "{} Job is Done!!! (Status : {})";

    // job이 실행되기 전에
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(BEFORE_MESSAGE, jobExecution.getJobInstance().getJobName());

    }

    // job이 실행된 후에
    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(AFTER_MESSAGE, jobExecution.getJobInstance().getJobName(),jobExecution.getStatus());

        if(jobExecution.getStatus() == BatchStatus.FAILED) {
            // 실패시 email 이나 메신저를 보내줄수 있음.
            log.info("Job is Failed!!!");
        }
    }
}
