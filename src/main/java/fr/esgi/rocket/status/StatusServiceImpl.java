package fr.esgi.rocket.status;

import fr.esgi.rocket.add.StagingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class StatusServiceImpl implements StatusService {

    private final StagingRepository stagingRepository;

    StatusServiceImpl(final StagingRepository stagingRepository) {
        this.stagingRepository = stagingRepository;
    }


    @Override
    public void getStatus() {
        stagingRepository.getAll().forEach(entry -> {
            log.info(entry.getFileName());
        });
    }
}
