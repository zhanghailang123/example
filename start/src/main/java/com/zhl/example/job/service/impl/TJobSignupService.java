package com.zhl.example.job.service.impl;
import java.time.LocalDateTime;

import com.zhl.example.job.api.SignUpRequest;
import com.zhl.example.job.dao.job.TJobSignupMapper;
import com.zhl.example.job.model.entity.job.TJobSignup;
import com.zhl.example.job.service.ITJobSignupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public void signUp(SignUpRequest request) {
        //1.参数校验
        checkParam(request);
        //2.本地存储
        saveSignUp(request);
        //3.同步调用下游接口 例如调用C端投递 调用B端IM 数据团队埋点接口
        try {
            //模拟调用
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkParam(SignUpRequest request) {
        //基础校验 。。。
        //风控校验 。。。
        //数据校验 。。。
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
