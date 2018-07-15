package com.jeedev.msdp.system.boot;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = {"classpath*:/spring/applicationContext-common.xml",
        "classpath*:/spring/applicationContext-dubbo.xml",
        "classpath*:/spring/applicationContext-aspect.xml",
        "classpath*:/spring/applicationContext-dataauth.xml",
        "classpath*:/spring/applicationContext-hibernate.xml",
        "classpath*:/dubbo/provider-*.xml",
        "classpath*:/spring/applicationContext-exception.xml"}) // 使用 provider.xml 配置
@ComponentScan(value = {"com.jeedev", "com.tansun"})
public class SpringBoot {
    private static final Logger logger = LoggerFactory.getLogger(SpringBoot.class);

    public static void main(String[] args) throws Exception {
        //SpringApplication.run(Application.class, args);
        logger.info("SpringBoot>>main：以jar包方式正在启动...");
        ApplicationContext ctx = SpringApplication.run(SpringBoot.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        System.out.println(Arrays.toString(beanNames));

    }
}
