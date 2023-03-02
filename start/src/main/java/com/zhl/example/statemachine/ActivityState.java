package com.zhl.example.statemachine;

public enum ActivityState {

    INIT(-1),
    NOT_START(0),
    DATA_PREPARING(1),
    DATA_PREPARED(2),
    DATA_PUSHING(3),
    FINISHED(4);

    private int state;
    private ActivityState(int state) {
        this.state = state;
    }
}
