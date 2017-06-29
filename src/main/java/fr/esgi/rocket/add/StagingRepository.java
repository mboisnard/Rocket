package fr.esgi.rocket.add;

import fr.esgi.rocket.core.repository.NitriteConnection;
import lombok.extern.slf4j.Slf4j;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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

        stagingEntries.forEach(entry -> {
            final Cursor<StagingEntry> cursor = repository.find(ObjectFilters.eq("fileName", entry.getFileName()));

            if(cursor.size() == 0) {
                repository.insert(entry);
            }
            else {
                repository.update(entry);
            }

            log.info(entry.getFileName() + " added to the staging area");
        });

        nitriteConnection.closeConnection();
    }

    public List<StagingEntry> getAll() {
        final Optional<Nitrite> connection =  nitriteConnection.getConnection();

        if (!connection.isPresent())
            throw new StagingException("This is not a rocket repository");

        final Nitrite nitrite = connection.get();
        final ObjectRepository<StagingEntry> repository = nitrite.getRepository(StagingEntry.class);
        final List<StagingEntry> entries = new ArrayList<>();

        repository.find().forEach(entry -> {
            entries.add(entry);
        });

        nitriteConnection.closeConnection();

        return entries;
    }
}
