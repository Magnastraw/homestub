package com.unc.home;

import com.unc.home.requests.HttpRequestManager;
import com.unc.home.smarthome.Home;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:stub.properties")
public class HomestubApplication {
	private final Home home;

	@Value("${house.id}")
	private String houseId;

	@Autowired
	public HomestubApplication(Home home) {
		this.home = home;
	}

	public static void main(String[] args) {
		SpringApplication.run(HomestubApplication.class);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			home.init();

			HttpRequestManager.postRequestObject(home.getHomeParameters(),"house",houseId);
			HttpRequestManager.postRequestList(home.getInventoryObjects(),"inventories",houseId);
		};
	}
}

