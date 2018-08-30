package com.boyang.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.boyang.listener.MyElasticJobListener;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

@Configuration
@ConditionalOnExpression("'${regCenter.serverList}'.length() > 0")
public class ZkRegCenterCfg {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${elaticjob.zookeeper.server-lists}") final String serverList, @Value("${elaticjob.zookeeper.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }

    /**
     * 设置活动监听，前提是已经设置好了监听，见下一个目录
     * @return
     */
    @Bean
    public ElasticJobListener elasticJobListener() {
        return new MyElasticJobListener(100, 100);
    }
}