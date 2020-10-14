package org.noear.solon.event;

/**
 * 事件监听者
 * */
public interface XEventListener<Event> {
    void onEvent(Event event);
}
