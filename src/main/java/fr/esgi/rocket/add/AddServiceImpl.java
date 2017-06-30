package fr.esgi.rocket.add;

import difflib.DiffUtils;
import difflib.Patch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
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

@Slf4j
@Service
class AddServiceImpl implements AddService {

    private final StagingRepository stagingRepository;

    private final String DB_NAME;

    AddServiceImpl(
        final StagingRepository stagingRepository,
        @Value("${info.rocket.nitrite.db}") final String dbName
    ) {
        this.stagingRepository = stagingRepository;
        this.DB_NAME = dbName;
    }

    @Override
    public void updateStagingArea(final String regex) {
        final Path workingDir = stringToPath(System.getProperty("user.dir"));
        final String absolutesRegex = workingDir + File.separator + regex;

        final List<String> projectFiles = recursiveListPaths(workingDir);
        final List<String> matchingFiles = getMatchingPaths(projectFiles, absolutesRegex);
        final List<StagingEntry> stagingEntries = matchingPathsToEntry(matchingFiles);

        addToStagingArea(stagingEntries);
    }

    @Override
    public List<String> recursiveListPaths(final Path path) {
        final List<String> files = new ArrayList<>();

        try (final DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (final Path entry : directoryStream) {
                if (entry.toFile().isDirectory()) {
                    files.addAll(recursiveListPaths(entry));
                }

                if(entry.toString().endsWith(DB_NAME))
                    continue;

                files.add(entry.toString());
            }
        }
        catch (final IOException e) {
            throw new StagingException(e);
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
        final StringBuilder content = new StringBuilder();
        final List<String> lines = fileToLines(path);

        if(!lines.isEmpty()) {
            lines.forEach(line -> content.append(line).append('\n'));
        }

        return StagingEntry.builder()
            .fileName(path)
            .diff(content.toString())
            //.diff(getDiff(path))
            .build();
    }

    @Override
    public String getDiff(final String path) {
        final List<String> commits = buildFileFromCommits(path);
        final List<String> newContent = fileToLines(path);

        final StringBuilder deltas = new StringBuilder();
        final Patch patch = DiffUtils.diff(commits, newContent);

        patch.getDeltas().forEach(delta -> deltas.append(delta.toString()).append("\n"));
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
