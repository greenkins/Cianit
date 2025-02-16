package ru.greenkins.housingm;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // Подключение к Consul
public class HousingMApplication {

	public static void main(String[] args) {
		SpringApplication.run(HousingMApplication.class, args);
	}

}
