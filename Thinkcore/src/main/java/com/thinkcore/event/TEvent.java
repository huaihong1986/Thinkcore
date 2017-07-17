package com.thinkcore.event;

public class TEvent {
    protected int mainEvent = -1;
    protected int subEvent = -1;
    protected Object params = null;

    public TEvent() {
    }

    public TEvent(int mainEvent) {
        this.mainEvent = mainEvent;
    }

    public TEvent(int mainEvent, int subEvent) {
        this.mainEvent = mainEvent;
        this.subEvent = subEvent;
    }

    public TEvent(int mainEvent, int subEvent, Object params) {
        this.mainEvent = mainEvent;
        this.subEvent = subEvent;
        this.params = params;
    }

    public TEvent(int mainEvent, Object params) {
        this.mainEvent = mainEvent;
        this.params = params;
    }

    public int getMainEvent() {
        return mainEvent;
    }

    public int getSubEvent() {
        return mainEvent;
    }

    public Object getParams() {
        return params;
    }
}
