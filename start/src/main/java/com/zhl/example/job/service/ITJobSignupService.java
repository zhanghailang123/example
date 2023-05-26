package com.zhl.example.job.service;

import com.zhl.example.job.api.SignUpRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhl.example.job.model.entity.job.TJobSignup;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhanghailang
 * @since 2023-05-24
 */
public interface ITJobSignupService extends IService<TJobSignup> {

    void signUp(SignUpRequest request);

    void mergeRequest(SignUpRequest request);
}
