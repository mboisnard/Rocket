package fr.esgi.rocket.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitCommand implements CommandLineRunner {
	
	private final String INIT_COMMAND;
	private final InitService initService;
	
	public InitCommand(@Value("${info.rocket.commands.init}") final String initCommand,
					   final InitService initService) {

		INIT_COMMAND = initCommand;
		this.initService = initService;
	}

	@Override
	public void run(final String... args) throws Exception {

		if (args.length > 0 && INIT_COMMAND.equals(args[0])) {
			System.out.println("Hello");
			//initService.initRepository();
		}
	}
}
