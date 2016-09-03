package org.ehuacui.ext.plugin.spring;

import com.jfinal.plugin.IPlugin;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.Reader;

/**
 * MyBatis JFinal Plugin
 * Created by jianwei.zhou on 2016/8/13.
 */
public class SpringPlugin implements IPlugin {

    private static final Logger logger = LoggerFactory.getLogger(SpringPlugin.class);
    private ApplicationContext applicationContext;

    @Override
    public boolean start() {
        try {
            applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
            return true;
        } catch (Exception e) {
            logger.error("MyBatis Init Failure", e);
            return false;
        }
    }

    @Override
    public boolean stop() {
        return true;
    }
}
