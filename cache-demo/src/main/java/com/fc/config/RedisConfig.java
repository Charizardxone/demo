package com.fc.config;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @author yfc
 * @date 2023/4/4 16:43
 */
@Component
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class RedisConfig {

    private String host;

    private Integer port;

    private Integer database;

    private String password;

    private Integer timeout;

    private Integer maxIdle;

    private Integer maxTotal;

    private String maxWaitMillis;

    private String minEvictableIdleTimeMillis;

    private Integer numTestsPerEvictionRun;

    private String timeBetweenEvictionRunsMillis;

    private boolean testOnBorrow;

    private boolean testWhileIdle;

    @Bean
    @Primary
    public LettuceConnectionFactory lettuceConnectionFactory() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle); // 连接池中的最小空闲连接
        genericObjectPoolConfig.setMaxTotal(maxTotal); // 连接池最大连接数（使用负值表示没有限制）
        genericObjectPoolConfig.setMaxWait(Duration.parse(maxWaitMillis)); // 连接池最大阻塞等待时间
        genericObjectPoolConfig.setTimeBetweenEvictionRuns(Duration.parse(timeBetweenEvictionRunsMillis));
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(""));
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(50000)) // 超时连接时间
                .shutdownTimeout(Duration.ofMillis(100)) // 在关闭客户端连接之前等待任务处理完成的最长时间，在这之后，无论任务是否执行完成，都会被执行器关闭，默认100ms
                .poolConfig(genericObjectPoolConfig)
                .build();

        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
//        factory.setShareNativeConnection(true);
        factory.setValidateConnection(false);
        return factory;
    }


    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){

        //redis连接配置
        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
        //设置连接的ip
        redisStandaloneConfiguration.setHostName(host);
        //设置密码
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        //端口号
        redisStandaloneConfiguration.setPort(port);
        //连接的数据库
        redisStandaloneConfiguration.setDatabase(database);
        //JedisConnectionFactory配置jedisPoolConfig
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        //客户端超时时间单位是毫秒
        jedisClientConfiguration.connectTimeout(Duration.ofMillis(timeout));
        //连接池
        jedisClientConfiguration.usePooling().poolConfig(jedisPoolConfig);
        //工厂对象
        return new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfiguration.build());
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        //创建连接池对象
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        //连接池最大连接数数据库数量
        jedisPoolConfig.setMaxTotal(maxTotal);
        //连接最大等待时间
        jedisPoolConfig.setMaxWait(Duration.parse(maxWaitMillis));
        //逐出连接最小空空闲时间
        jedisPoolConfig.setMinEvictableIdleTime(Duration.parse(minEvictableIdleTimeMillis));
        //每次逐出的最大数量
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        //逐出扫描时间的间隔
        jedisPoolConfig.setTimeBetweenEvictionRuns(Duration.parse(timeBetweenEvictionRunsMillis));
        //是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        //空闲时检查有效性
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }


}
