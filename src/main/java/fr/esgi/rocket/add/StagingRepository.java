package fr.esgi.rocket.add;

import fr.esgi.rocket.core.repository.NitriteConnection;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StagingRepository {

    private final NitriteConnection nitriteConnection;

    public StagingRepository(final NitriteConnection nitriteConnection) {
        this.nitriteConnection = nitriteConnection;
    }

    public void addToStaging(final List<StagingEntry> stagingEntries) {

        final Optional<Nitrite> connection =  nitriteConnection.getConnection();

        if (!connection.isPresent())
            throw new StagingException("This is not a rocket repository");

        final Nitrite nitrite = connection.get();
        final ObjectRepository<StagingEntry> repository = nitrite.getRepository(StagingEntry.class);

        repository.insert((StagingEntry[]) stagingEntries.toArray());

        nitriteConnection.closeConnection();
    }
}
