package fr.esgi.rocket.commit;

import fr.esgi.rocket.add.StagingEntry;
import fr.esgi.rocket.add.StagingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CommitServiceImpl implements CommitService {

    private final CommitRepository commitRepository;
    private final StagingRepository stagingRepository;

    CommitServiceImpl(final CommitRepository commitRepository, final StagingRepository stagingRepository) {
        this.commitRepository = commitRepository;
        this.stagingRepository = stagingRepository;
    }

    @Override
    public Commit commit(final String message) {
        final List<StagingEntry> entries = stagingRepository.getAll(true);

        final Commit commit = commitRepository.createCommit(entries, message);

        stagingRepository.deleteAll();

        return commit;
    }
}
