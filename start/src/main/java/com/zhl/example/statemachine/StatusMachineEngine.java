package com.zhl.example.statemachine;

/**
 * @author hailang.zhang
 * @since 2023-02-21
 */

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * 状态机工厂类
 */
public class StatusMachineEngine {

    private StatusMachineEngine() {
    }

    private static final Map<ChannelTypeEnum, String> STATUS_MACHINE_MAP = new HashMap();

    static {
        //短信推送状态
        STATUS_MACHINE_MAP.put(ChannelTypeEnum.SMS, "smsStateMachine");
        //PUSH推送状态
        STATUS_MACHINE_MAP.put(ChannelTypeEnum.PUSH, "pushStateMachine");
        //......
    }

    public static String getMachineEngine(ChannelTypeEnum channelTypeEnum) {
        return STATUS_MACHINE_MAP.get(channelTypeEnum);
    }

    /**
     * 触发状态转移
     *
     * @param channelTypeEnum
     * @param status 当前状态
     * @param eventType 触发事件
     * @param context 上下文参数
     */
    public static void fire(ChannelTypeEnum channelTypeEnum, ActivityState status, EventType eventType, Context context) {
        StateMachine orderStateMachine = StateMachineFactory.get(STATUS_MACHINE_MAP.get(channelTypeEnum));
        //推动状态机进行流转，具体介绍本期先省略
        orderStateMachine.fireEvent(status, eventType, context);
    }

    /**
     * 短信推送活动状态机初始化
     * 前些日子在CSDN发了个问题
     */

}