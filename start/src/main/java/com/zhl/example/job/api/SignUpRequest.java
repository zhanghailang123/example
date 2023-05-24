package com.zhl.example.job.api;

import lombok.Data;

/**
 * @author hailang.zhang
 * @since 2023-05-24
 */
@Data
public class SignUpRequest {

    private Long userId;
    private String jobNum;

}