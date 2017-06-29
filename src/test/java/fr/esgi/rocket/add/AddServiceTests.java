package fr.esgi.rocket.add;

import org.junit.Test;
import org.mockito.Mock;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class AddServiceTests {

    @Mock private StagingRepository stagingRepository;

    private AddService addService = new AddServiceImpl(stagingRepository);


    @Test
    public void shouldReturnANormalizedPath() {
        final String workingDir = System.getProperty("user.dir");

        final Path path = addService.stringToPath(workingDir);

        assertThat(path.toString()).isEqualTo(workingDir);
    }

    @Test
    public void shouldReturnDiff() {

        final String path = getClass().getClassLoader().getResource("diff.txt").getPath();

        final String diff = addService.getDiff(path);

        assertThat(diff).isEmpty();
    }
}
