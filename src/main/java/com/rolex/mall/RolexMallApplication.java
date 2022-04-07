package com.rolex.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * @author xiaos
 */
@MapperScan("com.rolex.mall.mapper")
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class RolexMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(RolexMallApplication.class, args);
    }

}
