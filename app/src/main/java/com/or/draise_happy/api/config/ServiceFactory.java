package com.or.draise_happy.api.config;


import com.or.draise_happy.api.ApiClient;
import com.or.draise_happy.api.HttpClient;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by dawabos on 2016/6/14.
 * Email dawabo@163.com
 */
public class ServiceFactory {


    public static ApiClient getProvideHttpService() {
        return provideService(ApiClient.class);
    }

    private static Map<Class, Object> m_service = new HashMap<Class, Object>();

    private static <T> T provideService(Class cls) {
        Object serv = m_service.get(cls);
        if (serv == null) {
            synchronized (cls) {
                serv = m_service.get(cls);
                if (serv == null) {
                    serv = HttpClient.getIns().createService(cls);
                    m_service.put(cls, serv);
                }
            }
        }
        return (T) serv;
    }
}
