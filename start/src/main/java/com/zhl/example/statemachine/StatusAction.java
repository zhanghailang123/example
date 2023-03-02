package com.zhl.example.statemachine;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import org.springframework.stereotype.Component;

/**
 * @author hailang.zhang
 * @since 2023-02-21
 */
@Component
public class StatusAction implements Action {

    @Override
    public void execute(Object from, Object to, Object event, Object context) {
        System.out.println("111111111");
    }
}