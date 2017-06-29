package fr.esgi.rocket.add;

import lombok.*;
import org.dizitart.no2.objects.Id;

import java.io.Serializable;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StagingEntry implements Serializable {

    @Id
    private String fileName;

    private String diff;
}
