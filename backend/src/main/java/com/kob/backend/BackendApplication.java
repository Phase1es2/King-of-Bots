package com.kob.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
// @MapperScan
/*
You probably accidentally put LoginService in a package that is scanned by MyBatis
as a Mapper (like @MapperScan("com.kob.backend.service")), which causes MyBatis
to try to treat it as a mapper interface.
* */
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
