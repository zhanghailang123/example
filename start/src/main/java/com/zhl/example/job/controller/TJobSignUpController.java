package com.zhl.example.job.controller;

import com.zhl.example.job.api.SignUpRequest;
import com.zhl.example.job.service.ITJobService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hailang.zhang
 * @since 2023-05-24
 */
@RestController
@RequestMapping("//t-job-signup")
public class TJobSignUpController {

    @Resource
    private ITJobService itJobService;

    @PostMapping("/signUp")
    public void signUp(SignUpRequest request) {
        this.itJobService.signUp(request);
    }

}