package com.example.elasticjob.config;

import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author yfc
 * @date 2023/9/1 16:35
 */
@Configuration
public class ElasticJobConfig {

    @Resource
    private CoordinatorRegistryCenter registryCenter;


    @Bean
    public JobConfiguration createJobConfiguration() {

        // 定义作业核心配置
        JobConfiguration jobConfig = JobConfiguration.newBuilder("myJob", 3)
                .cron("0/5 * * * * ?")
                .shardingItemParameters("0=text,1=image,2=radio")
                .failover(true)
                .overwrite(true)
                .monitorExecution(true)
                .misfire(true).build();

        //启动分布式定时任务
        new ScheduleJobBootstrap(registryCenter, new FileBackupJobDb(fileService), jobConfig).schedule();
        return jobConfig;
    }


}
