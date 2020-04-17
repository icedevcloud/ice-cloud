package com.icedevcloud.common.core.api;

import org.springframework.context.ApplicationContext;

/**
 * Spring 相关辅助类
 *
 * @author XiaoBingBy
 * @since 2020-4-17
 */
public class SpringHelper {

    private static ApplicationContext APPLICATION_CONTEXT;

    /**
     * 获取 applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    /**
     * 设置 applicationContext
     *
     * @param applicationContext
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (null == APPLICATION_CONTEXT) {
            APPLICATION_CONTEXT = applicationContext;
        }
    }

    /**
     * 通过class获取Bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return getApplicationContext().getBean(clazz);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

}
