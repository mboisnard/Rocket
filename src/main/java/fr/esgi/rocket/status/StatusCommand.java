package fr.esgi.rocket.status;

import fr.esgi.rocket.add.StagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StatusCommand implements CommandLineRunner {

    private final String STATUS_COMMAND;
    private final StatusService statusService;

    public StatusCommand(@Value("${info.rocket.commands.status}") final String statusCommand,
                         final StatusService statusService) {
        STATUS_COMMAND = statusCommand;
        this.statusService = statusService;
    }

    @Override
    public void run(final String... args) throws Exception {

        if (args.length > 0 && STATUS_COMMAND.equals(args[0])) {
            try {
                statusService.getStatus();
            } catch(StagingException e) {
                log.error(e.getLocalizedMessage());
            }

        }
    }
}
