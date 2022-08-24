package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class JedisComponent {

    @Bean
    public Jedis jedis() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.auth("123456");
        return jedis;
    }

}

