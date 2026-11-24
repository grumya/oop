package ru.nsu.msafonova1.task131;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс модульных тестов для проверки корректности поиска подстрок.
 */
class SubstringFinderTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void testFindSubstringsBasic() throws IOException {
        Path temporaryFile = temporaryDirectory.resolve("input.txt");
        Files.writeString(temporaryFile, "абракадабра", StandardCharsets.UTF_8);

        List<Long> actualIndices = SubstringFinder.findSubstrings(temporaryFile, "бра");
        List<Long> expectedIndices = List.of(1L, 8L);

        assertEquals(expectedIndices, actualIndices);
    }

    @Test
    void testFindSubstringsEmptyQuery() throws IOException {
        Path temporaryFile = temporaryDirectory.resolve("input.txt");
        Files.writeString(temporaryFile, "текст для проверки", StandardCharsets.UTF_8);

        List<Long> actualIndices = SubstringFinder.findSubstrings(temporaryFile, "");
        assertEquals(List.of(), actualIndices);
    }
}