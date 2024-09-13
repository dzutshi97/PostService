package com.instagram.common.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import redis.clients.jedis.Jedis;

public class PostServiceHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        //check for redis connectivity:
        Jedis jedis = new Jedis("localhost", 6379);
        String pong = jedis.ping();
        if (pong.equals("PONG")) {
            jedis.close();
            return new Health.Builder().up().withDetail("redis", "Redis is up and running!").build();
        }
        jedis.close();
        return new Health.Builder().down().withDetail("redis", "Redis is not running!").build();
    }
}
