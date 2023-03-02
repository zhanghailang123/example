package com.zhl.example.statemachine;

import com.alibaba.cola.statemachine.Condition;
import org.springframework.stereotype.Component;

/**
 * @author hailang.zhang
 * @since 2023-02-22
 */
@Component
public class StatusCondition implements Condition {
    Condition getInitCondition() {
        return a -> {
          return true;
        };
    }

    @Override
    public boolean isSatisfied(Object context) {
        return true;
    }

    @Override
    public String name() {
        return Condition.super.name();
    }
}