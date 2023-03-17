package com.ps.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = { "com.ps.*" })
public class WorkerApplication {

	public static void main(String[] args) throws JsonProcessingException {
		ConfigurableApplicationContext ctx = SpringApplication.run(WorkerApplication.class, args);
		Driver driver = ctx.getBean(Driver.class);

		try {
			driver.startProcess();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
