//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package org.lynn.duplicate.redis;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

@Repository("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource {
    @Resource
    private ShardedJedisPool shardedJedisPool;

    public RedisDataSourceImpl() {
    }

    public ShardedJedisPool getShardedJedisPool() {
        return this.shardedJedisPool;
    }

    public ShardedJedis getRedisClient() {
        try {
            ShardedJedis shardJedis = this.shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception var2) {
            return null;
        }
    }

    public void returnResource(ShardedJedis shardedJedis) {
        this.shardedJedisPool.returnResource(shardedJedis);
    }

    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if (broken) {
            this.shardedJedisPool.returnBrokenResource(shardedJedis);
        } else {
            this.shardedJedisPool.returnResource(shardedJedis);
        }

    }
}
