package com.zhl.example.job.service.impl;


import com.zhl.example.job.api.SignUpRequest;
import com.zhl.example.job.dao.job.TJobMapper;
import com.zhl.example.job.service.ITJobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhanghailang
 * @since 2023-05-24
 */
@Service
public class TJobService implements ITJobService {

    @Resource
    private TJobMapper tJobMapper;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        String jobNum = signUpRequest.getJobNum();
    }
}
