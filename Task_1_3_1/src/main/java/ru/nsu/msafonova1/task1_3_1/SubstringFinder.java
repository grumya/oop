package ru.nsu.msafonova1.task1_3_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для эффективного поиска всех вхождений подстроки в большом текстовом файле
 * с использованием потокового чтения и буферизации с нахлестом.
 */
public class SubstringFinder {

    /**
     * Находит индексы начала каждого вхождения заданной подстроки в файле.
     *
     * @param filePath путь к текстовому файлу в кодировке UTF-8
     * @param targetSubstring искомая подстрока
     * @return список индексов (смещений в символах), где была найдена подстрока
     * @throws IOException если произошла ошибка ввода-вывода при чтении файла
     */
    public static List<Long> findSubstrings(Path filePath, String targetSubstring) throws IOException {
        List<Long> occurrenceIndices = new ArrayList<>();

        if (targetSubstring == null || targetSubstring.isEmpty()) {
            return occurrenceIndices;
        }

        int substringLength = targetSubstring.length();
        int bufferSize = Math.max(8192, substringLength * 2);
        char[] characterBuffer = new char[bufferSize];

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            long globalCharacterOffset = 0;
            int overlapLength = substringLength - 1;
            int charactersReadCount;

            char[] overlapBuffer = new char[overlapLength];
            int currentOverlapLength = 0;

            while ((charactersReadCount = reader.read(characterBuffer, currentOverlapLength, bufferSize - currentOverlapLength)) != -1) {
                if (charactersReadCount == 0 && currentOverlapLength == 0) {
                    break;
                }

                int totalValidCharactersCount = charactersReadCount + currentOverlapLength;
                String currentTextumentBlock = new String(characterBuffer, 0, totalValidCharactersCount);

                int matchIndex = currentTextumentBlock.indexOf(targetSubstring);
                while (matchIndex != -1) {
                    long absoluteCharacterIndex = globalCharacterOffset + matchIndex - currentOverlapLength;
                    occurrenceIndices.add(absoluteCharacterIndex);

                    matchIndex = currentTextumentBlock.indexOf(targetSubstring, matchIndex + 1);
                }

                globalCharacterOffset += charactersReadCount;

                if (totalValidCharactersCount >= overlapLength) {
                    System.arraycopy(characterBuffer, totalValidCharactersCount - overlapLength, overlapBuffer, 0, overlapLength);
                    currentOverlapLength = overlapLength;
                } else {
                    System.arraycopy(characterBuffer, 0, overlapBuffer, 0, totalValidCharactersCount);
                    currentOverlapLength = totalValidCharactersCount;
                }

                System.arraycopy(overlapBuffer, 0, characterBuffer, 0, currentOverlapLength);
            }
        }

        return occurrenceIndices;
    }
}