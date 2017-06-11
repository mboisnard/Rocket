package fr.esgi.rocket.add;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AddCommand implements CommandLineRunner {

    private final String ADD_COMMAND;
    private final AddService addService;
    
    public AddCommand(@Value("${info.rocket.commands.add}") final String addCommand,
               final AddService addService) {
        ADD_COMMAND = addCommand;
        this.addService = addService;
    }

    @Override
    public void run(final String... args) throws Exception {

        if (args.length > 0 && ADD_COMMAND.equals(args[0])) {

        }
    }
}
