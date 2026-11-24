package ru.nsu.msafonova1.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.msafonova1.task141.model.ControlType;
import ru.nsu.msafonova1.task141.model.EducationForm;
import ru.nsu.msafonova1.task141.model.GradeBook;
import ru.nsu.msafonova1.task141.model.GradeRecord;
import ru.nsu.msafonova1.task141.model.GradeValue;
import ru.nsu.msafonova1.task141.service.GradeBookAnalyticsService;
import ru.nsu.msafonova1.task141.service.impl.AverageScoreCalculator;
import ru.nsu.msafonova1.task141.service.impl.BudgetTransferEvaluator;
import ru.nsu.msafonova1.task141.service.impl.HonorsDiplomaEvaluator;
import ru.nsu.msafonova1.task141.service.impl.IncreasedScholarshipEvaluator;

/**
 * Модульные тесты для проверки всех функций электронной зачетной книжки.
 */
class GradeBookAnalyticsServiceTest {
    private static final double SCORE_DELTA = 0.001;

    private GradeBookAnalyticsService analyticsService;

    @BeforeEach
    void setUp() {
        analyticsService = new GradeBookAnalyticsService(
                new AverageScoreCalculator(),
                new BudgetTransferEvaluator(),
                new HonorsDiplomaEvaluator(),
                new IncreasedScholarshipEvaluator()
        );
    }

    @Test
    void testAverageScoreCalculationIgnoresPassFailCredits() {
        GradeBook studentGradeBook = new GradeBook(
                "Иванов Иван Иванович",
                EducationForm.BUDGET,
                2
        );
        assertEquals(0.0, analyticsService.calculateCurrentAverageScore(studentGradeBook), SCORE_DELTA);

        studentGradeBook.addGradeRecord(
                new GradeRecord("Математический анализ", 1, ControlType.EXAM, GradeValue.EXCELLENT)
        );
        studentGradeBook.addGradeRecord(
                new GradeRecord("Алгебра и геометрия", 1, ControlType.EXAM, GradeValue.GOOD)
        );
        studentGradeBook.addGradeRecord(
                new GradeRecord("Физкультура", 1, ControlType.CREDIT, GradeValue.PASSED)
        );

        assertEquals(4.5, analyticsService.calculateCurrentAverageScore(studentGradeBook), SCORE_DELTA);
    }

    @Test
    void testBudgetTransferAllowedWithSatisfactoryInDifferentiatedCredit() {
        GradeBook paidStudentGradeBook = new GradeBook(
                "Петров Петр Петрович",
                EducationForm.PAID,
                3
        );
        paidStudentGradeBook.addGradeRecord(
                new GradeRecord("Дискретная математика", 1, ControlType.EXAM, GradeValue.GOOD)
        );
        paidStudentGradeBook.addGradeRecord(
                new GradeRecord("История", 1, ControlType.DIFFERENTIATED_CREDIT, GradeValue.SATISFACTORY)
        );
        paidStudentGradeBook.addGradeRecord(
                new GradeRecord("ООП", 2, ControlType.EXAM, GradeValue.EXCELLENT)
        );

        assertTrue(analyticsService.canTransferToBudget(paidStudentGradeBook));
    }

    @Test
    void testBudgetTransferDeniedWithSatisfactoryInExamOrInsufficientSessions() {
        GradeBook firstYearStudent = new GradeBook(
                "Новиков Олег",
                EducationForm.PAID,
                2
        );
        firstYearStudent.addGradeRecord(
                new GradeRecord("Матан", 1, ControlType.EXAM, GradeValue.EXCELLENT)
        );
        assertFalse(analyticsService.canTransferToBudget(firstYearStudent));

        GradeBook studentWithExamThree = new GradeBook(
                "Сидоров Алексей",
                EducationForm.PAID,
                4
        );
        studentWithExamThree.addGradeRecord(
                new GradeRecord("Матан", 1, ControlType.EXAM, GradeValue.SATISFACTORY)
        );
        studentWithExamThree.addGradeRecord(
                new GradeRecord("Алгебра", 2, ControlType.EXAM, GradeValue.GOOD)
        );
        studentWithExamThree.addGradeRecord(
                new GradeRecord("ООП", 3, ControlType.EXAM, GradeValue.SATISFACTORY)
        );

        assertFalse(analyticsService.canTransferToBudget(studentWithExamThree));
    }

    @Test
    void testBudgetTransferDeniedForBudgetStudent() {
        GradeBook budgetStudent = new GradeBook(
                "Смирнова Анна",
                EducationForm.BUDGET,
                4
        );
        assertFalse(analyticsService.canTransferToBudget(budgetStudent));
    }

    @Test
    void testHonorsDiplomaForecastWithRetakeAndThreshold() {
        // Задаем учебный план из 4 дисциплин (для 75% нужно минимум 3 пятерки)
        GradeBook studentGradeBook = new GradeBook(
                "Кузнецова Мария",
                EducationForm.BUDGET,
                3,
                4
        );

        // В 1 семестре получила тройку, но во 2 пересдала на отлично (учитывается последняя)
        studentGradeBook.addGradeRecord(
                new GradeRecord("ООП", 1, ControlType.EXAM, GradeValue.SATISFACTORY)
        );
        studentGradeBook.addGradeRecord(
                new GradeRecord("ООП", 2, ControlType.EXAM, GradeValue.EXCELLENT)
        );
        studentGradeBook.addGradeRecord(
                new GradeRecord("Базы данных", 2, ControlType.DIFFERENTIATED_CREDIT, GradeValue.GOOD)
        );

        // Сдано 2 дисциплины (одна 5, одна 4), осталось 2. Максимум: 3/4 = 75% -> шанс есть
        assertTrue(analyticsService.canGetHonorsDiploma(studentGradeBook));

        // Получает вторую четверку -> максимум 2/4 = 50% -> шанс потерян
        studentGradeBook.addGradeRecord(
                new GradeRecord("Учебная практика", 3, ControlType.PRACTICE_REPORT_DEFENSE, GradeValue.GOOD)
        );
        assertFalse(analyticsService.canGetHonorsDiploma(studentGradeBook));
    }

    @Test
    void testHonorsDiplomaDeniedIfQualifyingWorkIsNotExcellent() {
        GradeBook graduatingStudent = new GradeBook(
                "Волков Дмитрий",
                EducationForm.BUDGET,
                8,
                2
        );
        graduatingStudent.addGradeRecord(
                new GradeRecord("ООП", 2, ControlType.EXAM, GradeValue.EXCELLENT)
        );
        graduatingStudent.addGradeRecord(
                new GradeRecord("Алгоритмы", 3, ControlType.EXAM, GradeValue.EXCELLENT)
        );
        graduatingStudent.addGradeRecord(
                new GradeRecord("Дипломная работа", 8, ControlType.QUALIFYING_WORK_DEFENSE, GradeValue.GOOD)
        );

        assertFalse(analyticsService.canGetHonorsDiploma(graduatingStudent));
    }

    @Test
    void testIncreasedScholarshipEligibility() {
        GradeBook budgetStudent = new GradeBook(
                "Морозова Елена",
                EducationForm.BUDGET,
                3
        );
        // В 1 семестре была четверка, но стипендия в 3 семестре зависит только от 2 семестра
        budgetStudent.addGradeRecord(
                new GradeRecord("Матан", 1, ControlType.EXAM, GradeValue.GOOD)
        );
        budgetStudent.addGradeRecord(
                new GradeRecord("ООП", 2, ControlType.EXAM, GradeValue.EXCELLENT)
        );
        budgetStudent.addGradeRecord(
                new GradeRecord("Практикум", 2, ControlType.DIFFERENTIATED_CREDIT, GradeValue.EXCELLENT)
        );
        budgetStudent.addGradeRecord(
                new GradeRecord("Физкультура", 2, ControlType.CREDIT, GradeValue.PASSED)
        );

        assertTrue(analyticsService.canGetIncreasedScholarship(budgetStudent));
    }

    @Test
    void testValidationErrorsInModel() {
        GradeBook studentGradeBook = new GradeBook("Тестов Тест", EducationForm.BUDGET, 2);

        // Нельзя добавить оценку за будущий семестр
        assertThrows(IllegalArgumentException.class, () ->
                studentGradeBook.addGradeRecord(
                        new GradeRecord("Сети", 3, ControlType.EXAM, GradeValue.EXCELLENT)
                )
        );

        // Нельзя поставить зачет вместо цифровой оценки за экзамен
        assertThrows(IllegalArgumentException.class, () ->
                new GradeRecord("Матан", 1, ControlType.EXAM, GradeValue.PASSED)
        );
    }
}