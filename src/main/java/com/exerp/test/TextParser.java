package com.exerp.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class is the boot-strapping class. It initiates Spring Boot container. 
 * 
 * @author rab
 * 
 */
@SpringBootApplication
public class TextParser {

    public static void main(String[] args) {
        SpringApplication.run(TextParser.class, args);
    }
}
