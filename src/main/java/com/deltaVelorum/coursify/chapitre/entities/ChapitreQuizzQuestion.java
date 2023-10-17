package com.deltaVelorum.coursify.chapitre.entities;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChapitreQuizzQuestion {
    public String text;
    public ArrayList<ChapitreQuizzAnswer> answers = new ArrayList<>();

    public String getAnswersAsDelimitedString()
    {
        String str = answers.stream()
                .map(ChapitreQuizzAnswer::getText)
                .collect(Collectors.joining(","));
        return  str;
    }

    public String getAnswersIsCorrectAsDelimitedString()
    {
        String str = answers.stream()
                .map(ChapitreQuizzAnswer::getIsCorrect)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return  str;
    }

    @Override
    public String toString() {
        return "ChapitreQuizzQuestion{" +
                "text='" + text + '\'' +
                ", answers=" + answers +
                '}';
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public ArrayList<ChapitreQuizzAnswer> getAnswers() {
        return answers;
    }
    public void setAnswers(ArrayList<ChapitreQuizzAnswer> answers) {
        this.answers = answers;
    }
}
