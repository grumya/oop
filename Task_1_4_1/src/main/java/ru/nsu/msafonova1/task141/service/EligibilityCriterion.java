package ru.nsu.msafonova1.task141.service;

import ru.nsu.msafonova1.task141.model.GradeBook;

/**
 * Интерфейс критерия для проверки соответствия студента определенным академическим требованиям.
 */
public interface EligibilityCriterion {
    /**
     * Проверяет, выполняется ли условие для указанной зачетной книжки на текущем этапе обучения.
     *
     * @param gradeBook электронная зачетная книжка студента
     * @return {@code true}, если студент соответствует требованиям, иначе {@code false}
     */
    boolean isEligible(GradeBook gradeBook);
}