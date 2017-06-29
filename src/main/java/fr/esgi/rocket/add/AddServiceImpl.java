package fr.esgi.rocket.add;

import difflib.DiffUtils;
import difflib.Patch;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@Service
class AddServiceImpl implements AddService {

    private final StagingRepository stagingRepository;

    AddServiceImpl(final StagingRepository stagingRepository) {
        this.stagingRepository = stagingRepository;
    }

    @Override
    public List<String> recursiveListPaths(final Path path) throws IOException {
        final List<String> files = new ArrayList<>();

        try (final DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (final Path entry : directoryStream) {
                if (entry.toFile().isDirectory()) {
                    files.addAll(recursiveListPaths(entry));
                }

                files.add(entry.toString());
            }
        }

        return files;
    }

    @Override
    public List<String> getMatchingPaths(final List<String> paths, final String regex) {
        final Pattern pattern = Pattern.compile(regex);

        return paths.stream()
            .filter(path -> pattern.matcher(path).matches())
            .collect(toList());
    }

    @Override
    public Path stringToPath(final String path) {
        return Paths.get(path).normalize();
    }

    @Override
    public void addToStagingArea(final List<StagingEntry> stagingEntries) {
        stagingRepository.addToStaging(stagingEntries);
    }

    @Override
    public StagingEntry createEntry(final String path) {
        return StagingEntry.builder()
            .fileName(path)
            .diff(getDiff(path))
            .build();
    }

    @Override
    public String getDiff(final String path) {
        final List<String> commits = buildFileFromCommits(path);
        final List<String> newContent = fileToLines(path);

        final StringBuilder deltas = new StringBuilder();
        final Patch patch = DiffUtils.diff(commits, newContent);

        patch.getDeltas().forEach(delta -> deltas.append(delta.toString()).append("COUCOU"));
        return deltas.toString();
    }

    private List<String> buildFileFromCommits(final String path) {
        return new ArrayList<>();
    }

    @Override
    public List<StagingEntry> matchingPathsToEntry(final List<String> paths) {
        return paths.stream()
            .map(this::createEntry)
            .collect(toList());
    }

    private List<String> fileToLines(final String filename) {
        final List<String> lines = new LinkedList<>();
        String line = "";

        try (final BufferedReader in = new BufferedReader(new FileReader(filename))) {

            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (final IOException e) {
            throw new StagingException(e);
        }

        return lines;
    }

}
