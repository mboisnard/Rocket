package fr.esgi.rocket.commit;

import fr.esgi.rocket.add.StagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommitCommand implements CommandLineRunner {

    private final String COMMIT_COMMAND;
    private final CommitService commitService;

    public CommitCommand(@Value("${info.rocket.commands.commit}") final String commitCommand,
                         final CommitService commitService) {

        COMMIT_COMMAND = commitCommand;
        this.commitService = commitService;
    }

    @Override
    public void run(final String... args) throws Exception {

        if (args.length > 0 && COMMIT_COMMAND.equals(args[0])) {
            if(args.length != 2) {
                log.error("Usage: rocket commit [message]");
                return;
            }

            try {
                final String message = args[1];
                final Commit commit = commitService.commit(message);
                log.info("Created commit {}", commit.getHash());
            }
            catch(final StagingException e) {
                log.error(e.getLocalizedMessage());
            }
        }
    }
}
