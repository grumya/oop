package ru.nsu.msafonova1.task141.model;

/**
 * Виды контроля успеваемости согласно учебному плану ФИТ.
 */
public enum ControlType {
    /**
     * Задание.
     */
    ASSIGNMENT(false, false, false),

    /**
     * Контрольная работа.
     */
    TEST(false, false, false),

    /**
     * Коллоквиум.
     */
    COLLOQUIUM(false, false, false),

    /**
     * Экзамен.
     */
    EXAM(true, true, true),

    /**
     * Дифференцированный зачет.
     */
    DIFFERENTIATED_CREDIT(true, true, false),

    /**
     * Недифференцированный зачет.
     */
    CREDIT(false, false, false),

    /**
     * Защита отчета по практике.
     */
    PRACTICE_REPORT_DEFENSE(true, true, false),

    /**
     * Защита выпускной квалификационной работы (ВКР).
     */
    QUALIFYING_WORK_DEFENSE(true, false, false);

    private final boolean hasNumericGrade;
    private final boolean includedInDiplomaSupplement;
    private final boolean strictForBudgetTransfer;

    /**
     * Создает вид контроля с указанием его характеристик в учебном процессе.
     *
     * @param hasNumericGrade             {@code true}, если выставляется пятибалльная оценка
     * @param includedInDiplomaSupplement {@code true}, если оценка учитывается в 75% для диплома
     * @param strictForBudgetTransfer     {@code true}, если тройка блокирует перевод на бюджет
     */
    ControlType(
            boolean hasNumericGrade,
            boolean includedInDiplomaSupplement,
            boolean strictForBudgetTransfer
    ) {
        this.hasNumericGrade = hasNumericGrade;
        this.includedInDiplomaSupplement = includedInDiplomaSupplement;
        this.strictForBudgetTransfer = strictForBudgetTransfer;
    }

    /**
     * Возвращает признак наличия числовой оценки у данного вида контроля.
     *
     * @return {@code true}, если выставляется числовая оценка, иначе {@code false}
     */
    public boolean hasNumericGrade() {
        return hasNumericGrade;
    }

    /**
     * Возвращает признак включения дисциплины в расчет доли пятерок для диплома с отличием.
     *
     * @return {@code true}, если оценка идет в приложение к диплому, иначе {@code false}
     */
    public boolean isIncludedInDiplomaSupplement() {
        return includedInDiplomaSupplement;
    }

    /**
     * Возвращает признак строгого запрета оценки «удовлетворительно» при переводе на бюджет.
     *
     * @return {@code true}, если тройка по данному виду контроля запрещает перевод на бюджет
     */
    public boolean isStrictForBudgetTransfer() {
        return strictForBudgetTransfer;
    }
}