package fr.esgi.rocket.commit;

import fr.esgi.rocket.add.StagingEntry;
import fr.esgi.rocket.add.StagingException;
import fr.esgi.rocket.core.repository.NitriteConnection;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class CommitRepository {

    private final NitriteConnection nitriteConnection;

    public CommitRepository(final NitriteConnection nitriteConnection) {
        this.nitriteConnection = nitriteConnection;
    }

    public Commit createCommit(final List<StagingEntry> entries, final String message) {
        final Optional<Nitrite> connection =  nitriteConnection.getConnection();

        if (!connection.isPresent())
            throw new StagingException("This is not a rocket repository");

        final Nitrite nitrite = connection.get();
        final ObjectRepository<Commit> repository = nitrite.getRepository(Commit.class);

        final Commit commit = Commit.builder()
            .date(new Date())
            .entries(entries)
            .message(message)
            .hash(sha1(new Date().toString() + entries.hashCode()))
            .build();

        repository.insert(commit);

        return commit;
    }

    private String sha1(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            throw new StagingException(e);
        }
        return bytesToHex(md.digest(data.getBytes()));
    }

    private final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private  String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
