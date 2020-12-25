package com.aking.redis;

import redis.clients.jedis.Jedis;

/**
 * <p>
 *
 * </p>
 *
 * @author yk
 * @date 2020-12-23
 */
public class TestRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("39.98.238.47", 6381);
        jedis.auth("redis@123");
        System.out.println(jedis.ping());
    }
}
