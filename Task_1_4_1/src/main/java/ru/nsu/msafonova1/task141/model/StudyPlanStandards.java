package ru.nsu.msafonova1.task141.model;

/**
 * Нормативы учебного плана ФИТ по количеству видов контроля за весь период обучения.
 */
public final class StudyPlanStandards {
    /**
     * Общее количество экзаменов по учебному плану.
     */
    public static final int TOTAL_EXAMS_COUNT = 19;

    /**
     * Общее количество дифференцированных зачетов по учебному плану.
     */
    public static final int TOTAL_DIFFERENTIATED_CREDITS_COUNT = 35;

    /**
     * Общее количество защит отчетов по практике по учебному плану.
     */
    public static final int TOTAL_PRACTICE_DEFENSES_COUNT = 3;

    /**
     * Общее количество итоговых оценок дисциплин и практик, идущих в приложение к диплому.
     */
    public static final int TOTAL_DIPLOMA_SUPPLEMENT_DISCIPLINES =
            TOTAL_EXAMS_COUNT
                    + TOTAL_DIFFERENTIATED_CREDITS_COUNT
                    + TOTAL_PRACTICE_DEFENSES_COUNT;

    private StudyPlanStandards() {
    }
}