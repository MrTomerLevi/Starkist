package com.bytbale.starkist;

import com.bytbale.starkist.model.Job;
import com.bytbale.starkist.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StarkistApplication {

	private static final Logger log = LoggerFactory.getLogger(StarkistApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StarkistApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(JobRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Job("first job","0 19 14 * * ?", "/path/to/file.zip", Job.Source.HDFS));
			repository.save(new Job("second job","0 30 8 * * ?", "/path/to/file2.zip",Job.Source.HDFS));

			// fetch all customers
			log.info("Jobs found with findAll():");
			log.info("-------------------------------");
			for (Job customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");
		};
	}

}

