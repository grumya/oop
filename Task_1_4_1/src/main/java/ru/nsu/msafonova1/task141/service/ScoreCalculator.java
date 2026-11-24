package ru.nsu.msafonova1.task141.service;

import ru.nsu.msafonova1.task141.model.GradeBook;

/**
 * Интерфейс для расчета числовых показателей успеваемости по зачетной книжке.
 */
public interface ScoreCalculator {
    /**
     * Вычисляет числовой показатель на основе данных зачетной книжки.
     *
     * @param gradeBook электронная зачетная книжка студента
     * @return вычисленное числовое значение
     */
    double calculate(GradeBook gradeBook);
}