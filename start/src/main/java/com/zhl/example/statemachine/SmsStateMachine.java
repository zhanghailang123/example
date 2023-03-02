package com.zhl.example.statemachine;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import static com.zhl.example.statemachine.ActivityState.DATA_PREPARING;
import static com.zhl.example.statemachine.ActivityState.INIT;
import static com.zhl.example.statemachine.ActivityState.NOT_START;

/**
 * @author hailang.zhang
 * @since 2023-02-22
 */
@Component
public class SmsStateMachine implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private StatusAction smsStatusAction;

    @Autowired
    private StatusCondition smsStatusCondition;

    //基于DSL构建状态配置，触发事件转移和后续的动作
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        StateMachineBuilder<ActivityState, EventType, Context> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(INIT)
                .to(NOT_START)
                .on(EventType.TIME_BEGIN)
                .when(smsStatusCondition.getInitCondition())
                .perform(smsStatusAction);
        builder.externalTransition()
                .from(NOT_START)
                .to(DATA_PREPARING)
                .on(EventType.CAL_DATA)
                .when(smsStatusCondition)
                .perform(smsStatusAction);
        //            builder.externalTransition()
        //                    .from(DATA_PREPARING)
        //                    .to(DATA_PREPARED)
        //                    .on(EventType.PREPARED_DATA)
        //                    .when(smsStatusCondition.doNotifyAction())
        //                    .perform(smsStatusAction.doNotifyAction());
        builder.build(StatusMachineEngine.getMachineEngine(ChannelTypeEnum.SMS));
    }


}