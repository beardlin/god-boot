package net.lantrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *@Description
 *@Author dahuzi
 *@Date 2019/10/28  21:28
 */
@SpringBootApplication
@EnableScheduling//开启对定时任务的支持
public class GodBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(GodBootApplication.class, args);
    }

}
