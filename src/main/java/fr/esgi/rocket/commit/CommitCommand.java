package fr.esgi.rocket.commit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

        }
    }
}
