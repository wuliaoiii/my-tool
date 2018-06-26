package com.yangy.codetool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeToolApplication.class, args);
        System.out.println("=================代码生成器启动成功=================");
    }
}
