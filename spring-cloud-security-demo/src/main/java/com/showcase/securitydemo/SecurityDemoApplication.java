package com.showcase.securitydemo;

import com.showcase.securitydemo.config.ShowcaseProperties;
import com.showcase.securitydemo.config.ShowcaseProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
@EnableConfigurationProperties({ShowcaseProperties.class})
@MapperScan(basePackages = {"com.showcase.securitydemo.dao"})
public class SecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoApplication.class, args);
	}
}
