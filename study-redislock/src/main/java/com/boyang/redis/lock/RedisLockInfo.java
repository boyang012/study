package com.boyang.redis.lock;

public class RedisLockInfo {
	 private String lockId;
	  private String redisKey;
	  private Long expire;
	  private Long tryTimeout;
	  private int tryCount;

	  public RedisLockInfo(String lockId, String redisKey, Long expire, Long tryTimeout, int tryCount)
	  {
	    this.lockId = lockId;
	    this.redisKey = redisKey;
	    this.expire = expire;
	    this.tryTimeout = tryTimeout;
	    this.tryCount = tryCount;
	  }

	  public String getLockId() {
	    return this.lockId;
	  }

	  public void setLockId(String lockId) {
	    this.lockId = lockId;
	  }

	  public String getRedisKey() {
	    return this.redisKey;
	  }

	  public void setRedisKey(String redisKey) {
	    this.redisKey = redisKey;
	  }

	  public Long getExpire() {
	    return this.expire;
	  }

	  public void setExpire(Long expire) {
	    this.expire = expire;
	  }

	  public Long getTryTimeout() {
	    return this.tryTimeout;
	  }

	  public void setTryTimeout(Long tryTimeout) {
	    this.tryTimeout = tryTimeout;
	  }

	  public int getTryCount() {
	    return this.tryCount;
	  }

	  public void setTryCount(int tryCount) {
	    this.tryCount = tryCount;
	  }

	  public String toString()
	  {
	    return "RedisLockInfo{lockId='" + this.lockId + '\'' + ", redisKey='" + this.redisKey + '\'' + ", expire=" + this.expire + ", tryTimeout=" + this.tryTimeout + ", tryCount=" + this.tryCount + '}';
	  }
}
