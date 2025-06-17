package com.vab.transactionmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class TsmApplication {

	public static void main(String[] args) {

		TimeZone.setDefault(TimeZone.getTimeZone("GMT+05:30"));
		SpringApplication.run(TsmApplication.class, args);
	}

}
