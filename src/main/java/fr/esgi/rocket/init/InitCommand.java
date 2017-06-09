package fr.esgi.rocket.init;

import fr.esgi.rocket.core.command.Command;

public class InitCommand implements Command {
	
	private final InitService initService;
	
	public InitCommand(final InitService initService) {
		this.initService = initService;
	}
	
	@Override
	public void execute(final String... args) {
		initService.initRepository();
	}
}
