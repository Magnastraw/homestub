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

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter your key: ");


		String key = scanner.next();
		scanner.close();
		System.out.println(key);
		SpringApplication.run(HomestubApplication.class);
	}

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

//	@Bean
//	public CommandLineRunner run() throws Exception {
//		return args -> {
//			ObjectMapper objectMapper = new ObjectMapper();
//			Inventory inventory = new Inventory();
//			inventory.getInventoryFromDirectory(new File("src/main/resources/objects_new/home1"),0);
//
//			System.out.println(objectMapper.writeValueAsString(inventory.getInventoryObjectList()));
////			home.init();
////
////			HttpRequestManager.postRequestObject(home.getHomeParameters(),"house",houseId);
////			HttpRequestManager.postRequestList(home.getInventoryObjects(),"inventories",houseId);
//		};
//	}

	@Bean
	public CommandLineRunner schedulingRunner(TaskExecutor executor) {
		return args -> {
			executor.execute(new DirectoryWatcher(Paths.get(new File("src/main/resources/homes/home1/objects").getAbsolutePath()),home.getInventory(),home.getHouseId()));
		};

	}
}

