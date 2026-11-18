package ru.nsu.msafonova1.task131;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 - Класс для эффективного поиска всех вхождений подстроки в большом текстовом файле
 - с использованием потокового чтения и буферизации с нахлестом.
 */
public class SubstringFinder {

    private static final int MIN_BUFFER_SIZE = 8192;

    /**
     - Находит индексы начала каждого вхождения заданной подстроки в файле.
     *
     - @param filePath путь к текстовому файлу в кодировке UTF-8
     - @param targetSubstring искомая подстрока
     - @return список индексов (смещений в символах), где была найдена подстрока
     - @throws IOException если произошла ошибка ввода-вывода при чтении файла
     */
    public static List<Long> findSubstrings(
            Path filePath, String targetSubstring) throws IOException {
        List<Long> occurrenceIndices = new ArrayList<>();

        if (targetSubstring == null || targetSubstring.isEmpty()) {
            return occurrenceIndices;
        }

        int substringLength = targetSubstring.length();
        int bufferSize = Math.max(MIN_BUFFER_SIZE, substringLength * 2);
        CharBuffer characterBuffer = CharBuffer.allocate(bufferSize);

        try (BufferedReader reader = Files.newBufferedReader(
                filePath, StandardCharsets.UTF_8)) {
            long globalCharacterOffset = 0;
            int overlapLength = substringLength - 1;
            int charactersReadCount;

            CharBuffer overlapBuffer = CharBuffer.allocate(overlapLength);
            int currentOverlapLength = 0;

            while (true) {
                characterBuffer.limit(bufferSize);
                characterBuffer.position(currentOverlapLength);

                charactersReadCount = reader.read(characterBuffer);
                if (charactersReadCount == -1) {
                    break;
                }
                if (charactersReadCount == 0 && currentOverlapLength == 0) {
                    break;
                }

                int totalValidCharactersCount =
                        charactersReadCount + currentOverlapLength;

                characterBuffer.position(0);
                characterBuffer.limit(totalValidCharactersCount);
                String currentTextumentBlock = characterBuffer.toString();

                int matchIndex = currentTextumentBlock.indexOf(targetSubstring);
                while (matchIndex != -1) {
                    long absoluteCharacterIndex = globalCharacterOffset
                            + matchIndex - currentOverlapLength;
                    occurrenceIndices.add(absoluteCharacterIndex);

                    matchIndex = currentTextumentBlock.indexOf(
                            targetSubstring, matchIndex + 1);
                }

                globalCharacterOffset += charactersReadCount;

                if (totalValidCharactersCount >= overlapLength) {
                    overlapBuffer.clear();
                    characterBuffer.position(
                            totalValidCharactersCount - overlapLength
                    );
                    characterBuffer.limit(totalValidCharactersCount);
                    overlapBuffer.put(characterBuffer);
                    overlapBuffer.flip();
                    currentOverlapLength = overlapLength;
                } else {
                    overlapBuffer.clear();
                    characterBuffer.position(0);
                    characterBuffer.limit(totalValidCharactersCount);
                    overlapBuffer.put(characterBuffer);
                    overlapBuffer.flip();
                    currentOverlapLength = totalValidCharactersCount;
                }

                characterBuffer.clear();
                overlapBuffer.position(0);
                characterBuffer.put(overlapBuffer);
            }
        }

        return occurrenceIndices;
    }
}