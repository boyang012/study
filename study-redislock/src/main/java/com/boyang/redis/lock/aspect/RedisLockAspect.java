package com.boyang.redis.lock.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boyang.redis.lock.RedisLockFactory;
import com.boyang.redis.lock.RedisLockInfo;
import com.boyang.redis.lock.annotation.RedisLock;

@Aspect
@Component
public class RedisLockAspect
{

  @Autowired
  private RedisLockFactory redisLockFactory;

  private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockAspect.class);

  @Around("@annotation(redisLock)")
  public Object around(ProceedingJoinPoint point, RedisLock redisLock) throws Throwable {
    RedisLockInfo redisLockInfo = null;
    try {
      //String keyName = this.redisplusKeyGenerator.getKeyName(point, redisLock);
    	String keyName = redisLock.key();
      redisLockInfo = this.redisLockFactory.tryLock(keyName, redisLock.expire(), redisLock.tryTimeout());
      if (null != redisLockInfo) {
        Object result = point.proceed();
        return result;
      }

      if (null != redisLockInfo)
        this.redisLockFactory.releaseLock(redisLockInfo);
    }
    catch (Throwable e)
    {
      LOGGER.error("around exception", e);
      throw e;
    } finally {
      if (null != redisLockInfo) {
        this.redisLockFactory.releaseLock(redisLockInfo);
      }
    }
    return null;
  }
}