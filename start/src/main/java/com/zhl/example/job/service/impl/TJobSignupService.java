package com.zhl.example.job.service.impl;
import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhl.example.job.api.SignUpRequest;
import com.zhl.example.job.dao.job.TJobSignupMapper;
import com.zhl.example.job.model.entity.job.TJobSignup;
import com.zhl.example.job.service.ITJobSignupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhl.example.job.service.processor.BatchCommit;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhanghailang
 * @since 2023-05-24
 */
@Service
public class TJobSignupService extends ServiceImpl<TJobSignupMapper, TJobSignup> implements ITJobSignupService {


    /**
     * 有没有对公共资源进行抢占的场景呢
     * @param request
     */
    @Override
    public void signUp(SignUpRequest request) {
        //1.参数校验
//        if (!checkParam(request)) {
//            return;
//        }

        BatchCommit.add(request);
//        //2.本地存储
//        saveSignUp(request);
//        //3.同步调用下游接口 例如调用C端投递 调用B端IM 数据团队埋点接口
//        try {
//            //模拟调用
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void mergeRequest(SignUpRequest request) {
        BatchCommit.add(request);
    }

    private Boolean checkParam(SignUpRequest request) {
        //基础校验 。。。
        QueryWrapper<TJobSignup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("job_num", request.getJobNum());
        queryWrapper.eq("user_id", request.getUserId());
        Long count = this.baseMapper.selectCount(queryWrapper);
        if (count >= 0) {
            return false;
        }
        //风控校验 。。。
        //数据校验 。。。
        return true;
    }

    private void saveSignUp(SignUpRequest request) {
        TJobSignup signup = new TJobSignup();
        signup.setJobNum(request.getJobNum());
        signup.setUserId(request.getUserId());
        signup.setCreateTime(LocalDateTime.now());
        signup.setUpdateTime(LocalDateTime.now());
        save(signup);
    }

}
