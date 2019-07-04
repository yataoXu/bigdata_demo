package com.yatao.eisoo;

import com.yatao.eisoo.config.EsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({EsConfig.class})
public class EisooApplication {

    public static void main(String[] args) {
        SpringApplication.run(EisooApplication.class, args);
    }

}
