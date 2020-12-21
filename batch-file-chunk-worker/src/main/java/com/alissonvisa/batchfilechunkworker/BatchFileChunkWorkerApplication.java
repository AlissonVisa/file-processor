package com.alissonvisa.batchfilechunkworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableJms
public class BatchFileChunkWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchFileChunkWorkerApplication.class, args);
	}

}
