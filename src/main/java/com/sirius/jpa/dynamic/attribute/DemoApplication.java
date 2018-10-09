package com.sirius.jpa.dynamic.attribute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "com.sirius")
@ServletComponentScan(basePackages = {"com.sirius"})
public class DemoApplication {

    public static void main(String args[]) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
