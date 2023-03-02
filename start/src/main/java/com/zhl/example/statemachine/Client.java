package com.zhl.example.statemachine;

import static com.zhl.example.statemachine.ActivityState.INIT;

/**
 * @author hailang.zhang
 * @since 2023-02-22
 */
//调用端
public class Client {

    public static void main(String[] args) {
        //构建活动上下文
        Context context = new Context();
        // 触发状态流转
        StatusMachineEngine.fire(ChannelTypeEnum.SMS, INIT, EventType.SUBMIT, context);
    }
}