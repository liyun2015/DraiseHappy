package com.uban.rent.control;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Events 事件定义基类
 * Created by dawabos on 2016/8/11.
 * Email dawabo@163.com
 */
public class Events<T> {

    //所有事件的CODE
    public static final int EVENTS_NORMAL_TYPE = 0;
    public static final int EVENTS_SEARCH_TYPE = 1;
    public static final int EVENTS_USER_LOGIN = 2;
    public static final int EVENTS_SUBMIT_ORDER_USER_LOGIN = 4;

    //枚举
    @IntDef({EVENTS_NORMAL_TYPE,
            EVENTS_SEARCH_TYPE,
            EVENTS_USER_LOGIN,
            EVENTS_SUBMIT_ORDER_USER_LOGIN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventCode {}


    public @Events.EventCode int code;
    public T content;

    public static <O> Events<O> setContent(O t) {
        Events<O> events = new Events<>();
        events.content = t;
        return events;
    }

    public <T> T getContent() {
        return (T) content;
    }

}