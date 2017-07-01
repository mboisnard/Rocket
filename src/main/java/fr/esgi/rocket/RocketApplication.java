package fr.esgi.rocket;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RocketApplication {

	public static void main(final String[] args) {

		new SpringApplicationBuilder(RocketApplication.class)
			.logStartupInfo(false)
			.run(args);
	}
}
