package com.aking.redis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * <p>
 *  redis 事务
 * </p>
 *
 * @author yk
 * @date 2020-12-23
 */
public class TestTX {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("39.98.238.47", 6381);
        jedis.auth("redis@123");
        // 0执行前先清空数据，方便模拟场景
        jedis.flushDB();
        // 1.开启事务
        Transaction multi = jedis.multi();
        // 2.命令入队
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "aking");
        jsonObject.addProperty("age", "18");
        String result = new Gson().toJson(jsonObject);
        try {
            multi.set("k1", result);
            multi.set("k2", result);
            // 模拟异常
            int i = 1/0;
            // 3.执行事务
            multi.exec();
        } catch (Exception e) {
            // 丢弃事务
            multi.discard();
            e.printStackTrace();
        } finally {
            System.out.println(jedis.get("k1"));
            System.out.println(jedis.get("k2"));
            // 关闭连接
            jedis.close();
        }
    }
}
