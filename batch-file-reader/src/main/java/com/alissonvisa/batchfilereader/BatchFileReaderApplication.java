package com.alissonvisa.batchfilereader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BatchFileReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchFileReaderApplication.class, args);
	}

}
