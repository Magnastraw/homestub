package com.unc.home;

import com.unc.home.smarthome.Home;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;


@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:stub.properties")
public class HomestubApplication {
	private final Home home;

	@Autowired
	public HomestubApplication(Home home) {
		this.home = home;
	}

	public static void main(String[] args) {


		SpringApplication.run(HomestubApplication.class);
	}

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

	@Bean
	public CommandLineRunner schedulingRunner(TaskExecutor executor) {
		return args -> {
			executor.execute(new DirectoryWatcher(Paths.get(new File("src/main/resources/homes/home1/objects").getAbsolutePath()),home.getInventory()));
		};

	}
}

