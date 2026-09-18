package org.example.studyroom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.studyroom.mapper")
public class StudyroomApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyroomApplication.class, args);
    }

}
