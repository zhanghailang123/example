package com.zhl.example.job.service.processor;

import com.zhl.example.job.api.SignUpRequest;
import com.zhl.example.job.dao.job.TJobSignupMapper;
import com.zhl.example.job.model.entity.job.TJobSignup;
import com.zhl.example.job.service.ITJobSignupService;
import com.zhl.example.job.service.impl.TJobSignupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hailang.zhang
 * @since 2023-05-26
 */
@Slf4j
@Service
public class BatchCommit {
    @Resource
    private ITJobSignupService jobSignupService;

    private static LinkedBlockingQueue<SignUpRequest> cache = new LinkedBlockingQueue();
    private static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(1);

    @PostConstruct
    public void init() {
        EXECUTOR.scheduleAtFixedRate(() -> {
            if (cache.size() == 0) {
                log.info("队列缓存中数据为空");
                return;
            }
            log.info("此次合并了多少请求: {}", cache.size());
            List<TJobSignup> list = new ArrayList<TJobSignup>();
            while (!cache.isEmpty()) {
                TJobSignup signup = new TJobSignup();
                try {
                    signup.setUserId(cache.take().getUserId());
                    signup.setJobNum(cache.take().getJobNum());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                signup.setCreateTime(LocalDateTime.now());
                signup.setUpdateTime(LocalDateTime.now());
                list.add(signup);
            }

            this.jobSignupService.saveBatch(list);
        }, 0 , 3, TimeUnit.SECONDS);
    }

    public static void add(SignUpRequest request) {
        cache.offer(request);
    }
}