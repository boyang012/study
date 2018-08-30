package com.boyang.event;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
 
/**
 * 如果想把作业运行的内容写到DB中，我们需要用到另一个构造器，
 * 同时定义自己的JobEventConfiguration，
 * 目前来说实现这个接口的只有一个类JobEventRdbConfiguration，
 * 通过这个可以将作业运行的痕迹进行持久化到DB的操作。
 * @author shudalei
 */
@Configuration
public class EventCfg {
  @Lazy
  @Autowired
  private DataSource dataSource;
 
  @Bean
  public JobEventConfiguration jobEventConfiguration() {
    return new JobEventRdbConfiguration(dataSource);
  }
}