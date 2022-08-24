package com.example.demo.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

@RestController
public class NumberController {

    private AtomicInteger count = new AtomicInteger(0);

    @Resource
    private Jedis jedis;

    @PutMapping("/number")
    public synchronized void subNumber() throws InterruptedException {
        int number = Integer.parseInt(jedis.get("number"));
        Thread.sleep(1000);
        number-=1;
        jedis.set("number" , Integer.toString(number));
        count.addAndGet(1);
        System.out.println(count.get());
    }
}
