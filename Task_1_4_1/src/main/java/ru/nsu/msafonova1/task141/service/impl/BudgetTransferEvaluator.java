package ru.nsu.msafonova1.task141.service.impl;

import java.util.List;
import java.util.Objects;
import ru.nsu.msafonova1.task141.model.EducationForm;
import ru.nsu.msafonova1.task141.model.GradeBook;
import ru.nsu.msafonova1.task141.model.GradeRecord;
import ru.nsu.msafonova1.task141.model.GradeValue;
import ru.nsu.msafonova1.task141.service.EligibilityCriterion;

/**
 * Оценщик возможности перевода студента с платной формы обучения на бюджетную.
 */
public class BudgetTransferEvaluator implements EligibilityCriterion {
    private static final int REQUIRED_COMPLETED_SESSIONS = 2;

    /**
     * Проверяет, может ли студент перевестись с платного обучения на бюджет.
     * Перевод возможен при отсутствии академических задолженностей и оценок
     * «удовлетворительно» по экзаменам за последние две завершенные сессии.
     *
     * @param gradeBook электронная зачетная книжка студента
     * @return {@code true}, если студент имеет право на перевод, иначе {@code false}
     */
    @Override
    public boolean isEligible(GradeBook gradeBook) {
        Objects.requireNonNull(gradeBook, "Зачетная книжка не может быть null");

        if (gradeBook.getEducationForm() == EducationForm.BUDGET) {
            return false;
        }

        int lastCompletedSemester = gradeBook.getCurrentSemesterNumber() - 1;
        if (lastCompletedSemester < REQUIRED_COMPLETED_SESSIONS) {
            return false;
        }

        int previousCompletedSemester = lastCompletedSemester - 1;
        List<GradeRecord> allGradeRecords = gradeBook.getGradeRecords();

        for (GradeRecord record : allGradeRecords) {
            boolean isFromLastTwoSessions = record.semesterNumber() == lastCompletedSemester
                    || record.semesterNumber() == previousCompletedSemester;

            if (!isFromLastTwoSessions) {
                continue;
            }

            if (!record.gradeValue().isPositiveResult()) {
                return false;
            }

            if (record.controlType().isStrictForBudgetTransfer()
                    && record.gradeValue() == GradeValue.SATISFACTORY) {
                return false;
            }
        }

        return true;
    }
}