package com.zhl.example.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhl.example.job.api.SignUpRequest;
import com.zhl.example.job.model.entity.job.TJob;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhanghailang
 * @since 2023-05-24
 */
public interface ITJobService{

    /**
     * 报名接口
     * @param signUpRequest 请求
     */
    void signUp(SignUpRequest signUpRequest);
}
