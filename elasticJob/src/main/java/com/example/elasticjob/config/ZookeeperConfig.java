package com.example.elasticjob.config;

import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author yfc
 * @date 2023/9/1 16:30
 */
@Component
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperConfig {

    private  String url;
    //定时任务命名空间
    private  String nameSpace;

    //zk的配置及创建注册中心
    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter coordinatorRegistryCenter(){
        //zk的配置
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(url, nameSpace);
        //设置zk超时时间
        zookeeperConfiguration.setSessionTimeoutMilliseconds(1000);
        //创建注册中心
        CoordinatorRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        return zookeeperRegistryCenter;
    }

}
