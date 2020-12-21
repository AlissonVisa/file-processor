package com.alissonvisa.salesmanapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;

@SpringBootApplication
public class SalesmanApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesmanApiApplication.class, args);
	}

}
