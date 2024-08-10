package com.example.lms;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LmsApplication {
	private static final Logger logger = LogManager.getLogger(LmsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
		String workingDirectory = System.getProperty("user.dir");
		System.out.println("Current Working Directory: " + workingDirectory);
		logger.info("Logger has started");
	}

}
