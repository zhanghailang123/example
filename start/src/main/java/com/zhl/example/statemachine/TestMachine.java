package com.zhl.example.statemachine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zhl.example.statemachine.ActivityState.INIT;

/**
 * @author hailang.zhang
 * @since 2023-02-22
 */
@RestController(value = "/statemachine")
public class TestMachine {

    @GetMapping("/fire")
    public void fireMachine() {
        //使用状态机的好处在哪儿呀
        //构建活动上下文
        Context context = new Context();
        // 触发状态流转
        StatusMachineEngine.fire(ChannelTypeEnum.SMS, INIT, EventType.TIME_BEGIN, context);
    }
}