package ru.nsu.msafonova1.task141.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.nsu.msafonova1.task141.model.EducationForm;
import ru.nsu.msafonova1.task141.model.GradeBook;
import ru.nsu.msafonova1.task141.model.GradeRecord;
import ru.nsu.msafonova1.task141.model.GradeValue;
import ru.nsu.msafonova1.task141.service.EligibilityCriterion;

/**
 * Оценщик возможности получения повышенной академической стипендии в текущем семестре.
 */
public class IncreasedScholarshipEvaluator implements EligibilityCriterion {
    /**
     * Проверяет, имеет ли студент право на повышенную стипендию в текущем семестре
     * по результатам предыдущего завершенного семестра.
     *
     * @param gradeBook электронная зачетная книжка студента
     * @return {@code true}, если студент претендует на повышенную стипендию, иначе {@code false}
     */
    @Override
    public boolean isEligible(GradeBook gradeBook) {
        Objects.requireNonNull(gradeBook, "Зачетная книжка не может быть null");

        if (gradeBook.getEducationForm() != EducationForm.BUDGET) {
            return false;
        }

        int previousSemesterNumber = gradeBook.getCurrentSemesterNumber() - 1;
        if (previousSemesterNumber < 1) {
            return false;
        }

        List<GradeRecord> previousSemesterRecords = new ArrayList<>();
        for (GradeRecord record : gradeBook.getGradeRecords()) {
            if (record.semesterNumber() == previousSemesterNumber) {
                previousSemesterRecords.add(record);
            }
        }

        if (previousSemesterRecords.isEmpty()) {
            return false;
        }

        for (GradeRecord record : previousSemesterRecords) {
            if (!record.gradeValue().isPositiveResult()) {
                return false;
            }
            if (record.controlType().hasNumericGrade()
                    && record.gradeValue() != GradeValue.EXCELLENT) {
                return false;
            }
        }

        return true;
    }
}