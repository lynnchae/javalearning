package org.lynn.duplicate.redis;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Class Name : org.lynn
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018/7/18 11:58
 */
public interface RedisDataSource {
    ShardedJedisPool getShardedJedisPool();

    ShardedJedis getRedisClient();

    void returnResource(ShardedJedis var1);

    void returnResource(ShardedJedis var1, boolean var2);
}