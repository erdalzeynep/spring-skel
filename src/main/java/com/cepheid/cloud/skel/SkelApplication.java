package com.cepheid.cloud.skel;

import com.cepheid.cloud.skel.controller.ItemController;
import com.cepheid.cloud.skel.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackageClasses = {ItemController.class, SkelApplication.class})
@EnableJpaRepositories(basePackageClasses = {ItemRepository.class})
public class SkelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkelApplication.class, args);
    }
}
