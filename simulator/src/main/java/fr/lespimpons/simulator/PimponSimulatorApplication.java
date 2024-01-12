package fr.lespimpons.simulator;

import fr.lespimpons.simulator.component.FireTruckSingleton;
import fr.lespimpons.simulator.controller.SensorController;
import fr.lespimpons.simulator.object.Fire;
import fr.lespimpons.simulator.component.FireSingleton;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;
@EnableScheduling
@SpringBootApplication
public class PimponSimulatorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PimponSimulatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

	@Bean
	public FireSingleton fireSingleton() {
		return FireSingleton.getInstance();
	}

	@Bean
	public FireTruckSingleton fireTruckSingleton() {
		return FireTruckSingleton.getInstance();
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}



}
