package com.example.demo.config;

import com.example.demo.lock.JedisLock;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class NumberFroJedisController {

    private AtomicInteger jedisCount = new AtomicInteger(0);

    @Resource
    private Jedis jedis;

    @PutMapping("/jedisNumber")
    public void subNumber() throws InterruptedException {
        String requestId = UUID.randomUUID().toString();
        String LOCKKEY = "LOCKKEY";
        if(JedisLock.tryGetDistributedLock(jedis , LOCKKEY , requestId , 3000)){
            Integer number = Integer.parseInt(jedis.get("jedisNumber"));
            Thread.sleep(1000);
            number-=1;
            jedis.set("jedisNumber" , number.toString());
            jedisCount.addAndGet(1);
            System.out.println(jedisCount.get());
            JedisLock.releaseDistributedLock(jedis , LOCKKEY , requestId);
        }
    }
}
