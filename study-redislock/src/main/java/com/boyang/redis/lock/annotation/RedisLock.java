package com.boyang.redis.lock.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock
{
  public abstract String key();

  public abstract long expire();

  public abstract long tryTimeout();
}