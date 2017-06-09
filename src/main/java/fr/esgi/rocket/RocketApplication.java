package fr.esgi.rocket;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RocketApplication implements CommandLineRunner {

	public static void main(final String[] args) {
		SpringApplication.run(RocketApplication.class, args);
	}
	
	@Override
	public void run(final String... args) throws Exception {
		for(String arg : args) {
			System.out.println(arg);
		}
	}
}
