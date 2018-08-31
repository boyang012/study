package com.boyang.redis.lock;

import java.util.Collections;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class RedisLockFactory {

	@Autowired
	private RedisTemplate redisTemplate;
	private static final String LUA_SCRIPT_LOCK = "return redis.call('set',KEYS[1],ARGV[1],'NX','PX',ARGV[2])";
	private static final RedisScript<String> SCRIPT_LOCK = new DefaultRedisScript(
			"return redis.call('set',KEYS[1],ARGV[1],'NX','PX',ARGV[2])", String.class);
	private static final String LUA_SCRIPT_UNLOCK = "if redis.call('get',KEYS[1]) == ARGV[1] then return tostring(redis.call('del', KEYS[1])) else return '0' end";
	private static final RedisScript<String> SCRIPT_UNLOCK = new DefaultRedisScript(
			"if redis.call('get',KEYS[1]) == ARGV[1] then return tostring(redis.call('del', KEYS[1])) else return '0' end",
			String.class);

	public RedisLockInfo tryLock(String redisKey, long expire, long tryTimeout) {
		Assert.isTrue(tryTimeout > 0L, "tryTimeout必须大于0");
		long timestamp = System.currentTimeMillis();
		int tryCount = 0;
		String lockId = UUID.randomUUID().toString();
		while (System.currentTimeMillis() - timestamp < tryTimeout) {
			try {
				Object lockResult = this.redisTemplate.execute(SCRIPT_LOCK, this.redisTemplate.getStringSerializer(),
						this.redisTemplate.getStringSerializer(), Collections.singletonList(redisKey),
						new Object[] { lockId, String.valueOf(expire) });

				tryCount++;
				if ((null != lockResult) && ("OK".equals(lockResult))) {
					return new RedisLockInfo(lockId, redisKey, Long.valueOf(expire), Long.valueOf(tryTimeout),
							tryCount);
				}
				Thread.sleep(50L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean releaseLock(RedisLockInfo redisLockInfo) {
		Object releaseResult = null;
		try {
			releaseResult = this.redisTemplate.execute(SCRIPT_UNLOCK, this.redisTemplate.getStringSerializer(),
					this.redisTemplate.getStringSerializer(), Collections.singletonList(redisLockInfo.getRedisKey()),
					new Object[] { redisLockInfo.getLockId() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (null != releaseResult) && (releaseResult.equals(Integer.valueOf(1)));
	}
}