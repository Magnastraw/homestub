package com.unc.home;

import com.unc.home.smarthome.Home;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

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
	public RestTemplate restTemplate() {
		return new RestTemplate(clientHttpRequestFactory());
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(20000);
		factory.setConnectTimeout(20000);
		factory.setConnectionRequestTimeout(20000);
		return factory;
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

