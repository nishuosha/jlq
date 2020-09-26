package com.test.jlq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hao
 * @Date 2020/9/14
 */
@SpringBootApplication
@MapperScan(basePackages = "com.test.jlq.repository")
public class SpringBootRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRunner.class, args);
    }

}
