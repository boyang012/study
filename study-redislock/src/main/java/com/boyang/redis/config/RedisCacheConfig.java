package com.boyang.redis.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.boyang.redis.lock.aspect.RedisLockAspect;


@Configuration
@EnableCaching
@AutoConfigureAfter({RedisCacheConfig.class})
@Import({RedisLockAspect.class})
public class RedisCacheConfig extends CachingConfigurerSupport {
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory factory) {
	    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();  // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
	    config = config.entryTtl(Duration.ofMinutes(1))     // 设置缓存的默认过期时间，也是使用Duration设置
	            .disableCachingNullValues();     // 不缓存空值

	    // 设置一个初始化的缓存空间set集合
	    Set<String> cacheNames =  new HashSet<>();
	    cacheNames.add("my-redis-cache1");
	    cacheNames.add("my-redis-cache2");

	    // 对每个缓存空间应用不同的配置
	    Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
	    configMap.put("my-redis-cache1", config);
	    configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));

	    RedisCacheManager cacheManager = RedisCacheManager.builder(factory)     // 使用自定义的缓存配置初始化一个cacheManager
	            .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
	            .withInitialCacheConfigurations(configMap)
	            .build();
	    return cacheManager;
	}
    
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String,String>();
        redisTemplate.setConnectionFactory(factory);
        // key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
        // 所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
        // 或者JdkSerializationRedisSerializer序列化方式;
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();// Long类型不可以会出现异常信息;
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        return redisTemplate;
    }
    
}