package com.star.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author zhangrc
 * @create 2020-11-29
 */
@Slf4j
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext context;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            context = applicationContext;
            // ===== 在项目初始化bean后检验数据库连接是否
            DataSource dataSource = (DataSource) context.getBean("dataSource");
            dataSource.getConnection().close();
        } catch (Exception e) {
            log.error("数据连接错误：url="+url+";username="+username+";password="+password);
//            e.printStackTrace();
            // ===== 当检测数据库连接失败时, 停止项目启动
//            System.exit(-1);
        }
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }
}
