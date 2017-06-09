package fr.esgi.rocket.add;

import fr.esgi.rocket.core.command.Command;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AddCommand implements Command, CommandLineRunner {

    private final String ADD_COMMAND;
    private final AddService addService;
    
    AddCommand(@Value("${info.rocket.commands.add") final String addCommand,
               final AddService addService) {
        ADD_COMMAND = addCommand;
        this.addService = addService;
    }

    @Override
    public void execute(final String... args) {}

    @Override
    public void run(final String... args) throws Exception {

        if (args.length > 0 && ADD_COMMAND.equals(args[0])) {

        }
    }
}
