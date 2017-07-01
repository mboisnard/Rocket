package fr.esgi.rocket.add;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
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
            if(args.length != 2) {
                log.error("Usage: rocket add [regex]");
                return;
            }

            try {
                final String regex = args[1];
                addService.updateStagingArea(regex);
            }
            catch(final StagingException e) {
                log.error(e.getLocalizedMessage());
            }
        }
    }
}
