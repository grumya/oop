package ru.nsu.msafonova1.task141.model;

import java.util.Objects;

/**
 * Неизменяемая запись об одной аттестации в зачетной книжке студента.
 *
 * @param disciplineName название учебной дисциплины или практики
 * @param semesterNumber номер семестра (от 1 до 8)
 * @param controlType    вид контроля успеваемости
 * @param gradeValue     полученная оценка или результат зачета
 */
public record GradeRecord(
        String disciplineName,
        int semesterNumber,
        ControlType controlType,
        GradeValue gradeValue
) {
    private static final int MINIMUM_SEMESTER = 1;
    private static final int MAXIMUM_SEMESTER = 8;

    /**
     * Проверяет корректность переданных параметров при создании записи.
     */
    public GradeRecord {
        Objects.requireNonNull(disciplineName, "Название дисциплины не может быть null");
        Objects.requireNonNull(controlType, "Вид контроля не может быть null");
        Objects.requireNonNull(gradeValue, "Оценка не может быть null");

        if (disciplineName.isBlank()) {
            throw new IllegalArgumentException("Название дисциплины не может быть пустым");
        }
        if (semesterNumber < MINIMUM_SEMESTER || semesterNumber > MAXIMUM_SEMESTER) {
            throw new IllegalArgumentException("Номер семестра должен быть от 1 до 8");
        }
        if (controlType.hasNumericGrade() && gradeValue.getNumericScore().isEmpty()) {
            throw new IllegalArgumentException(
                    "Для вида контроля " + controlType + " требуется числовая оценка"
            );
        }
        if (!controlType.hasNumericGrade() && gradeValue.getNumericScore().isPresent()) {
            throw new IllegalArgumentException(
                    "Для вида контроля " + controlType + " требуется нечисловой результат (зачет/незачет)"
            );
        }
    }
}