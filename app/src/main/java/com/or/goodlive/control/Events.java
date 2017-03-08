package com.or.goodlive.control;

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
    public static final int EVENTS_USER_LOGIN_OUT = 3;
    public static final int EVENTS_DISMISS_POP = 4;
    public static final int EVENTS_UPDATA_HEADER = 5;
    public static final int EVENTS_FIRST = 6;
    public static final int EVENTS_NEW_MESSAGE = 7;
    public static final int EVENTS_CANSEL_MESSAGE = 8;
    public static final int EVENTS_COMMENT_NUM = 9;

    //枚举
    @IntDef({EVENTS_USER_LOGIN_OUT,
            EVENTS_NORMAL_TYPE,
            EVENTS_SEARCH_TYPE,
            EVENTS_DISMISS_POP,
            EVENTS_UPDATA_HEADER,
            EVENTS_FIRST,
            EVENTS_NEW_MESSAGE,
            EVENTS_CANSEL_MESSAGE,
            EVENTS_COMMENT_NUM
            })
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