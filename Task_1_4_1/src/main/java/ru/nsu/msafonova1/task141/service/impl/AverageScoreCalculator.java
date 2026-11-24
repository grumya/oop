package ru.nsu.msafonova1.task141.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import ru.nsu.msafonova1.task141.model.GradeBook;
import ru.nsu.msafonova1.task141.model.GradeRecord;
import ru.nsu.msafonova1.task141.service.ScoreCalculator;

/**
 * Калькулятор текущего среднего балла студента за все время обучения.
 */
public class AverageScoreCalculator implements ScoreCalculator {
    /**
     * Вычисляет средний балл по всем дисциплинам, имеющим пятибалльную оценку.
     * Недифференцированные зачеты при расчете не учитываются.
     *
     * @param gradeBook электронная зачетная книжка студента
     * @return текущий средний балл или {@code 0.0}, если числовых оценок еще нет
     */
    @Override
    public double calculate(GradeBook gradeBook) {
        Objects.requireNonNull(gradeBook, "Зачетная книжка не может быть null");
        List<GradeRecord> allGradeRecords = gradeBook.getGradeRecords();

        double numericGradesSum = 0.0;
        int numericGradesCount = 0;

        for (GradeRecord record : allGradeRecords) {
            OptionalInt numericScore = record.gradeValue().getNumericScore();
            if (numericScore.isPresent()) {
                numericGradesSum += numericScore.getAsInt();
                numericGradesCount++;
            }
        }

        if (numericGradesCount == 0) {
            return 0.0;
        }

        return numericGradesSum / numericGradesCount;
    }
}