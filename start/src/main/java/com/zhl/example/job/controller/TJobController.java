package com.zhl.example.job.controller;

import com.zhl.example.job.service.ITJobService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhanghailang
 * @since 2023-05-24
 */
@RestController
@RequestMapping("//t-job")
public class TJobController {

    @Resource
    private ITJobService itJobService;


}
