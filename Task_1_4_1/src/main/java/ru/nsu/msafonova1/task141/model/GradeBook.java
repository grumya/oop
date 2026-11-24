package ru.nsu.msafonova1.task141.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Электронная зачетная книжка студента.
 */
public class GradeBook {
    private static final int MINIMUM_SEMESTER = 1;
    private static final int MAXIMUM_SEMESTER = 8;

    private final String studentFullName;
    private final EducationForm educationForm;
    private final int currentSemesterNumber;
    private final int plannedDiplomaDisciplinesCount;
    private final List<GradeRecord> gradeRecords;

    /**
     * Создает зачетную книжку со стандартным количеством дисциплин учебного плана ФИТ.
     *
     * @param studentFullName       полное имя студента
     * @param educationForm         форма обучения (бюджетная или платная)
     * @param currentSemesterNumber текущий семестр обучения (от 1 до 8)
     */
    public GradeBook(
            String studentFullName,
            EducationForm educationForm,
            int currentSemesterNumber
    ) {
        this(
                studentFullName,
                educationForm,
                currentSemesterNumber,
                StudyPlanStandards.TOTAL_DIPLOMA_SUPPLEMENT_DISCIPLINES
        );
    }

    /**
     * Создает зачетную книжку с заданным общим количеством дисциплин в учебном плане.
     *
     * @param studentFullName                полное имя студента
     * @param educationForm                  форма обучения (бюджетная или платная)
     * @param currentSemesterNumber          текущий семестр обучения (от 1 до 8)
     * @param plannedDiplomaDisciplinesCount общее количество итоговых оценок по учебному плану
     */
    public GradeBook(
            String studentFullName,
            EducationForm educationForm,
            int currentSemesterNumber,
            int plannedDiplomaDisciplinesCount
    ) {
        this.studentFullName = Objects.requireNonNull(
                studentFullName,
                "ФИО студента не может быть null"
        );
        this.educationForm = Objects.requireNonNull(
                educationForm,
                "Форма обучения не может быть null"
        );
        if (studentFullName.isBlank()) {
            throw new IllegalArgumentException("ФИО студента не может быть пустым");
        }
        if (currentSemesterNumber < MINIMUM_SEMESTER
                || currentSemesterNumber > MAXIMUM_SEMESTER) {
            throw new IllegalArgumentException("Текущий семестр должен быть от 1 до 8");
        }
        if (plannedDiplomaDisciplinesCount < 1) {
            throw new IllegalArgumentException(
                    "Количество дисциплин по учебному плану должно быть положительным"
            );
        }
        this.currentSemesterNumber = currentSemesterNumber;
        this.plannedDiplomaDisciplinesCount = plannedDiplomaDisciplinesCount;
        this.gradeRecords = new ArrayList<>();
    }

    /**
     * Добавляет новую запись об аттестации в зачетную книжку.
     *
     * @param gradeRecord запись об оценке
     */
    public void addGradeRecord(GradeRecord gradeRecord) {
        Objects.requireNonNull(gradeRecord, "Запись об оценке не может быть null");
        if (gradeRecord.semesterNumber() > currentSemesterNumber) {
            throw new IllegalArgumentException(
                    "Нельзя добавить оценку за будущий семестр: " + gradeRecord.semesterNumber()
            );
        }
        gradeRecords.add(gradeRecord);
    }

    /**
     * Возвращает полное имя студента.
     *
     * @return ФИО студента
     */
    public String getStudentFullName() {
        return studentFullName;
    }

    /**
     * Возвращает текущую форму обучения студента.
     *
     * @return форма обучения
     */
    public EducationForm getEducationForm() {
        return educationForm;
    }

    /**
     * Возвращает номер текущего семестра обучения.
     *
     * @return номер текущего семестра
     */
    public int getCurrentSemesterNumber() {
        return currentSemesterNumber;
    }

    /**
     * Возвращает плановое количество итоговых оценок для приложения к диплому.
     *
     * @return количество дисциплин по учебному плану
     */
    public int getPlannedDiplomaDisciplinesCount() {
        return plannedDiplomaDisciplinesCount;
    }

    /**
     * Возвращает неизменяемый список всех записей об оценках в зачетной книжке.
     *
     * @return список записей об оценках
     */
    public List<GradeRecord> getGradeRecords() {
        return Collections.unmodifiableList(gradeRecords);
    }
}