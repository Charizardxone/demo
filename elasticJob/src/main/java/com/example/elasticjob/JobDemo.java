package com.example.elasticjob;

import com.example.elasticjob.config.MyThreadLocal;
import com.example.elasticjob.job.MyJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author yfc
 * @date 2023/11/20 9:50
 */
@Slf4j
@RestController
@RequestMapping("")
public class JobDemo {

    @Autowired
    private CoordinatorRegistryCenter coordinatorRegistryCenter;

    @Autowired
    private JobConfiguration jobConfiguration;

    private static Integer i = 1;

    @PostConstruct
    public void schedule() {
        log.info("schedule start");
        MyJob myJob = new MyJob();
        MyThreadLocal.setThreadLocal("0");
        new ScheduleJobBootstrap(coordinatorRegistryCenter, myJob, jobConfiguration).schedule();
    }

    @GetMapping("addJob")
    public String addJob(){

        MyJob myJob = new MyJob();

        JobConfiguration configuration = JobConfiguration.newBuilder("job" + i, 1)
                .cron("0/2 * * * * ?")
                .shardingItemParameters("0=text,1=image,2=radio")
                .failover(true)
                .overwrite(true)
                .monitorExecution(true)
                .misfire(true).build();

        new ScheduleJobBootstrap(coordinatorRegistryCenter, myJob, configuration).schedule();
        i++;
        return "addJob success";
    }


    public static void main(String[] args) {

        new Thread(()->{
            MyThreadLocal.setThreadLocal("aaaa");
            System.out.println(MyThreadLocal.getThreadLocal());
        }).start();

        new Thread(()->{
            MyThreadLocal.setThreadLocal("bbbb");
            System.out.println(MyThreadLocal.getThreadLocal());
        }).start();

    }
}
