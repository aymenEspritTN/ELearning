package com.DeltaVelorum.Coursify.Chapitre;

import java.util.Arrays;

public class ChapitreQuizz {
    private int id;
    private String[] questions;
    private int[] answers; //correct answers
    private int chapitreId;

    @Override
    public String toString() {
        return "Quizz{" +
                "id=" + id +
                ", questions=" + Arrays.toString(questions) +
                ", correctAnswers=" + Arrays.toString(answers) +
                ", chapitreId=" + chapitreId +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public void setAnswers(int[] answers) {
        this.answers = answers;
    }

    public void setChapitreId(int chapitreId) {
        this.chapitreId = chapitreId;
    }

    public int getId() {
        return id;
    }

    public String[] getQuestions() {
        return questions;
    }

    public int[] getAnswers() {
        return answers;
    }

    public int getChapitreId() {
        return chapitreId;
    }
}
