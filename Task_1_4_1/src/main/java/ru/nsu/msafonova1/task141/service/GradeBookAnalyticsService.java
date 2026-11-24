package ru.nsu.msafonova1.task141.service;

import java.util.Objects;
import ru.nsu.msafonova1.task141.model.GradeBook;

/**
 * Сервис аналитики успеваемости студента, объединяющий все расчеты по зачетной книжке.
 */
public class GradeBookAnalyticsService {
    private final ScoreCalculator averageScoreCalculator;
    private final EligibilityCriterion budgetTransferEvaluator;
    private final EligibilityCriterion honorsDiplomaEvaluator;
    private final EligibilityCriterion increasedScholarshipEvaluator;

    /**
     * Создает сервис аналитики с заданными реализациями правил расчета и проверок.
     *
     * @param averageScoreCalculator        калькулятор среднего балла
     * @param budgetTransferEvaluator       критерий проверки возможности перевода на бюджет
     * @param honorsDiplomaEvaluator        критерий прогноза получения диплома с отличием
     * @param increasedScholarshipEvaluator критерий проверки права на повышенную стипендию
     */
    public GradeBookAnalyticsService(
            ScoreCalculator averageScoreCalculator,
            EligibilityCriterion budgetTransferEvaluator,
            EligibilityCriterion honorsDiplomaEvaluator,
            EligibilityCriterion increasedScholarshipEvaluator
    ) {
        this.averageScoreCalculator = Objects.requireNonNull(
                averageScoreCalculator,
                "Калькулятор среднего балла не может быть null"
        );
        this.budgetTransferEvaluator = Objects.requireNonNull(
                budgetTransferEvaluator,
                "Критерий перевода на бюджет не может быть null"
        );
        this.honorsDiplomaEvaluator = Objects.requireNonNull(
                honorsDiplomaEvaluator,
                "Критерий диплома с отличием не может быть null"
        );
        this.increasedScholarshipEvaluator = Objects.requireNonNull(
                increasedScholarshipEvaluator,
                "Критерий повышенной стипендии не может быть null"
        );
    }

    /**
     * Вычисляет текущий средний балл студента за все время обучения.
     *
     * @param gradeBook электронная зачетная книжка
     * @return текущий средний балл
     */
    public double calculateCurrentAverageScore(GradeBook gradeBook) {
        Objects.requireNonNull(gradeBook, "Зачетная книжка не может быть null");
        return averageScoreCalculator.calculate(gradeBook);
    }

    /**
     * Проверяет возможность перевода студента с платной на бюджетную форму обучения.
     *
     * @param gradeBook электронная зачетная книжка
     * @return {@code true}, если перевод возможен, иначе {@code false}
     */
    public boolean canTransferToBudget(GradeBook gradeBook) {
        Objects.requireNonNull(gradeBook, "Зачетная книжка не может быть null");
        return budgetTransferEvaluator.isEligible(gradeBook);
    }

    /**
     * Определяет, сохраняется ли возможность получения диплома с отличием.
     *
     * @param gradeBook электронная зачетная книжка
     * @return {@code true}, если красный диплом возможен, иначе {@code false}
     */
    public boolean canGetHonorsDiploma(GradeBook gradeBook) {
        Objects.requireNonNull(gradeBook, "Зачетная книжка не может быть null");
        return honorsDiplomaEvaluator.isEligible(gradeBook);
    }

    /**
     * Проверяет возможность получения повышенной стипендии в текущем семестре.
     *
     * @param gradeBook электронная зачетная книжка
     * @return {@code true}, если студент претендует на повышенную стипендию, иначе {@code false}
     */
    public boolean canGetIncreasedScholarship(GradeBook gradeBook) {
        Objects.requireNonNull(gradeBook, "Зачетная книжка не может быть null");
        return increasedScholarshipEvaluator.isEligible(gradeBook);
    }
}