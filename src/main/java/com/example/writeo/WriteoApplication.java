package com.example.writeo;

import org.hibernate.HibernateException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class WriteoApplication {
	 static final Logger logger = Logger.getLogger("com.example.writeo");
	public static void main(String[] args) {
		try
		{
			SpringApplication.run(WriteoApplication.class, args);
		}
		catch(HibernateException e)
		{
			logger.log(Level.SEVERE, "Error with Database: ");
			System.exit(0);
		}
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				logger.log(Level.INFO, beanName);
			}

		};
	}
}
