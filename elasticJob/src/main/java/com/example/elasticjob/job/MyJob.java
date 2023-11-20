package com.example.elasticjob.job;

import com.example.elasticjob.config.MyThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * @author yfc
 * @date 2023/9/1 16:49
 */
@Slf4j
@Component
public class MyJob implements SimpleJob {


    @Override
    public void execute(ShardingContext context) {

        log.info("jobName：{}", context.getJobName());

    }
}

