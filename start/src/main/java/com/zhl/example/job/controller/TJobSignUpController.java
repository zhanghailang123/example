package com.zhl.example.job.controller;

import com.zhl.example.job.api.SignUpRequest;
import com.zhl.example.job.service.ITJobService;
import com.zhl.example.job.service.ITJobSignupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hailang.zhang
 * @since 2023-05-24
 */
@RestController
@RequestMapping("/signup")
public class TJobSignUpController {

    @Resource
    private ITJobSignupService itJobSignupService;

    @PostMapping("/signUp")
    public void signUp(@RequestBody SignUpRequest request) {
        this.itJobSignupService.signUp(request);
    }

}