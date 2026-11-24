package ru.nsu.msafonova1.task141.model;

import java.util.OptionalInt;

/**
 * Возможные оценки и результаты аттестации в зачетной книжке.
 */
public enum GradeValue {
    /**
     * Оценка «отлично» (5).
     */
    EXCELLENT(5, true),

    /**
     * Оценка «хорошо» (4).
     */
    GOOD(4, true),

    /**
     * Оценка «удовлетворительно» (3).
     */
    SATISFACTORY(3, true),

    /**
     * Оценка «неудовлетворительно» (2).
     */
    UNSATISFACTORY(2, false),

    /**
     * Результат недифференцированного зачета «зачтено».
     */
    PASSED(null, true),

    /**
     * Результат недифференцированного зачета «не зачтено».
     */
    FAILED(null, false);

    private final Integer numericScore;
    private final boolean positiveResult;

    /**
     * Создает элемент перечисления оценки.
     *
     * @param numericScore   числовой эквивалент оценки или {@code null} для обычного зачета
     * @param positiveResult {@code true}, если аттестация пройдена без академической задолженности
     */
    GradeValue(Integer numericScore, boolean positiveResult) {
        this.numericScore = numericScore;
        this.positiveResult = positiveResult;
    }

    /**
     * Возвращает числовой балл оценки, если он предусмотрен.
     *
     * @return {@link OptionalInt} с числовым баллом или пустое значение для обычного зачета
     */
    public OptionalInt getNumericScore() {
        return numericScore == null ? OptionalInt.empty() : OptionalInt.of(numericScore);
    }

    /**
     * Проверяет, является ли результат положительным (без задолженности).
     *
     * @return {@code true}, если оценка положительная, иначе {@code false}
     */
    public boolean isPositiveResult() {
        return positiveResult;
    }
}