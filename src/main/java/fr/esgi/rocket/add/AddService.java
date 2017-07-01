package fr.esgi.rocket.add;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

interface AddService {

    void updateStagingArea(String regex);

    List<String> recursiveListPaths(Path path) throws IOException;

    List<String> getMatchingPaths(List<String> paths, String regex);

    Path stringToPath(String path);

    void addToStagingArea(List<StagingEntry> stagingEntries);

    StagingEntry createEntry(String path);

    String getDiff(String path);

    List<StagingEntry> matchingPathsToEntry(List<String> paths);
}
