package com.boyang.conf;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.boyang.job.MyJob;
import com.boyang.listener.MyElasticJobListener;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

@Component
public class SimpleJobCfg {
	/**
	   * 注册中心
	   */
	  @Resource
	  private ZookeeperRegistryCenter regCenter;
	  /**
	   * job事件配置
	   */
	  @Lazy
	  @Resource
	  private JobEventConfiguration jobEventConfiguration;
	 
	  
	  @Resource
	  private MyElasticJobListener elasticJobListeners;
	  /**
	   * 微信access token获取任务对象
	   *
	   */
	  @Resource
	  private MyJob simpleJob;
	 
	  /**
	   *
	   * @param cron 定时任务cron配置
	   * @param shardingTotalCount 任务分片数
	   * @param shardingItemParameters 任务分片参数
	   * @return JobScheduler 任务调度器
	   */
	  @Bean(initMethod = "init")
	  public JobScheduler simpleJobScheduler(@Value("${simpleJob.cron}") final String cron,
	                      @Value("${simpleJob.shardingTotalCount}") final int shardingTotalCount,
	                      @Value("${simpleJob.shardingItemParameters}") final String shardingItemParameters) {
	    return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters), elasticJobListeners) ;
	    /*return new SpringJobScheduler(simpleJob, regCenter,
	        getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters),
	        jobEventConfiguration);*/
	  }
	 
	  /**
	   *
	   * @param jobClass 任务调度类
	   * @param cron 定时任务cron配置
	   * @param shardingTotalCount 任务分片数
	   * @param shardingItemParameters 任务分片参数
	   * @return LiteJobConfiguration 任务配置
	   */
	  private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends com.dangdang.ddframe.job.api.simple.SimpleJob> jobClass, final String cron,
	                             final int shardingTotalCount, final String shardingItemParameters) {
	    return LiteJobConfiguration
	        .newBuilder(
	            new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
	                .shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName()))
	        .overwrite(true).build();
	  }
}
