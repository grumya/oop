package ru.nsu.msafonova1.task141.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import ru.nsu.msafonova1.task141.model.ControlType;
import ru.nsu.msafonova1.task141.model.GradeBook;
import ru.nsu.msafonova1.task141.model.GradeRecord;
import ru.nsu.msafonova1.task141.model.GradeValue;
import ru.nsu.msafonova1.task141.service.EligibilityCriterion;

/**
 * Оценщик и прогнозист возможности получения диплома с отличием («красного» диплома).
 */
public class HonorsDiplomaEvaluator implements EligibilityCriterion {
    private static final double REQUIRED_EXCELLENT_RATIO = 0.75;

    /**
     * Проверяет, сохраняется ли возможность получения диплома с отличием
     * на текущем этапе обучения.
     *
     * @param gradeBook электронная зачетная книжка студента
     * @return {@code true}, если красный диплом возможен, иначе {@code false}
     */
    @Override
    public boolean isEligible(GradeBook gradeBook) {
        Objects.requireNonNull(gradeBook, "Зачетная книжка не может быть null");
        List<GradeRecord> allGradeRecords = gradeBook.getGradeRecords();

        if (!isQualifyingWorkEligible(allGradeRecords)) {
            return false;
        }

        Map<String, GradeRecord> finalDiplomaGrades = extractFinalDiplomaGrades(allGradeRecords);

        int currentExcellentCount = 0;
        for (GradeRecord finalRecord : finalDiplomaGrades.values()) {
            GradeValue gradeValue = finalRecord.gradeValue();
            if (!gradeValue.isPositiveResult() || gradeValue == GradeValue.SATISFACTORY) {
                return false;
            }
            if (gradeValue == GradeValue.EXCELLENT) {
                currentExcellentCount++;
            }
        }

        int completedDisciplinesCount = finalDiplomaGrades.size();
        int totalPlannedDisciplinesCount = Math.max(
                gradeBook.getPlannedDiplomaDisciplinesCount(),
                completedDisciplinesCount
        );
        int remainingDisciplinesCount = totalPlannedDisciplinesCount - completedDisciplinesCount;

        int maximumPossibleExcellentCount = currentExcellentCount + remainingDisciplinesCount;
        double maximumPossibleExcellentRatio =
                (double) maximumPossibleExcellentCount / totalPlannedDisciplinesCount;

        return maximumPossibleExcellentRatio >= REQUIRED_EXCELLENT_RATIO;
    }

    private boolean isQualifyingWorkEligible(List<GradeRecord> allGradeRecords) {
        GradeValue latestQualifyingWorkGrade = null;
        for (GradeRecord record : allGradeRecords) {
            if (record.controlType() == ControlType.QUALIFYING_WORK_DEFENSE) {
                latestQualifyingWorkGrade = record.gradeValue();
            }
        }
        return latestQualifyingWorkGrade == null
                || latestQualifyingWorkGrade == GradeValue.EXCELLENT;
    }

    private Map<String, GradeRecord> extractFinalDiplomaGrades(List<GradeRecord> allGradeRecords) {
        Map<String, GradeRecord> latestGradesByDiscipline = new LinkedHashMap<>();
        for (GradeRecord record : allGradeRecords) {
            if (record.controlType().isIncludedInDiplomaSupplement()) {
                latestGradesByDiscipline.put(record.disciplineName(), record);
            }
        }
        return latestGradesByDiscipline;
    }
}