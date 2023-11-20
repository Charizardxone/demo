package com.example.elasticjob.config;

import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yfc
 * @date 2023/9/1 16:35
 */
@Configuration
public class ElasticJobConfig {


    @Bean
    public JobConfiguration createJobConfiguration() {

        return JobConfiguration.newBuilder("myJob0", 1)
                .cron("0/5 * * * * ?")
                .shardingItemParameters("0=text,1=image,2=radio")
                .failover(true)
                .overwrite(true)
                .monitorExecution(true)
                .misfire(true).build();
    }


}
