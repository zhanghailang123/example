package com.example.springplugin.controller;

import cn.hutool.http.HttpUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author hailang.zhang
 * @since 2023-07-19
 */
@Slf4j
@RestController
public class TestTomcatController {

    @GetMapping("/getTest")
    public void getTest(int num) throws Exception {
        log.info("{} 接受到请求：num={}", Thread.currentThread().getName(), num);
        TimeUnit.HOURS.sleep(1);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            new Thread(() -> {
                HttpUtil.get("127.0.0.1:8090/getTest?num=" + finalI);
            }).start();
        }
        Thread.yield();
    }
}