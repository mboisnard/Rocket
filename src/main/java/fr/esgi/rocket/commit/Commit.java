package fr.esgi.rocket.commit;

import fr.esgi.rocket.add.StagingEntry;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.dizitart.no2.objects.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Getter @Setter
public class Commit implements Serializable {

    @Id
    private String hash;

    private Date date;

    private String message;

    private List<StagingEntry> entries;
}
