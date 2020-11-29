package com.star.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description :TODO
 * @author zhangrc
 * @date 2019/7/15 10:38
 * @version 1.0
 */
@Component
public class SpringBeanLoader implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 获取SpringApplicationContext
     *
     * @return ApplicationContext
     */

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 设置SpringApplicationContext
     *
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringBeanLoader.applicationContext = applicationContext;
    }

    /**
     * 获取Spring中注册的Bean
     *
     * @param beanClass
     * @param beanId
     * @return
     */
    public static <T> T getSpringBean(String beanId, Class<T> beanClass) {
        return getApplicationContext().getBean(beanId, beanClass);
    }

    /**
     * 获取Spring中注册的Bean
     *
     * @param beanClass
     * @return
     */
    public static <T> T getSpringBean(Class<T> beanClass) {
        return getApplicationContext().getBean(beanClass);
    }

}
