package com.it;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.it.mapper")
public class Application {
    public static void main(String[] args) {
        System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification","true");
        SpringApplication.run(Application.class,args);
    }
}
