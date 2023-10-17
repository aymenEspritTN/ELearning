package com.deltaVelorum.coursify.chapitre.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ChapitreQuizz { // the format in DB: q1|q2 a11,a12,a13|a21 etc..
    private int id;
    private ArrayList<ChapitreQuizzQuestion> questions = new ArrayList<>();
    private int chapitreId;

    public ChapitreQuizz(int _chapitreId) //only this in constructor bc its the only HARD required field.
    {
        chapitreId = _chapitreId;
    }
    public String getQuestionsAsDelimitedString() {
        String str = questions.stream()
                .map(ChapitreQuizzQuestion::getText)
                .collect(Collectors.joining("|"));
        return  str;
    }
    public String getAnswersAsDelimitedString()
    {
        String str = questions.stream()
                .map(ChapitreQuizzQuestion::getAnswersAsDelimitedString)
                .collect(Collectors.joining("|"));
        return  str;
    }
    public String getAnswersIsCorrectAsDelimitedString()
    {
        String str = questions.stream()
                .map(ChapitreQuizzQuestion::getAnswersIsCorrectAsDelimitedString)
                .collect(Collectors.joining("|"));
        return  str;
    }

    public static ArrayList<ChapitreQuizzQuestion> makeQuestionsFromDelimitedString(
            String delimitedString, String delimitedAnswers, String delimitedCorrectAnswers)
    {
        ArrayList<ChapitreQuizzQuestion> arr = new ArrayList<>();
        // split(delimiter) by default removes trailing empty strings from result array
        // -1 makes it keep them
        String[] questions = delimitedString.split("\\|", -1);
        String[] answers = delimitedAnswers.split("\\|", -1);
        String[] correctAnswers = delimitedCorrectAnswers.split("\\|", -1);
        for(var i=0; i<questions.length; i++)
        {
            String questionText = questions[i];
            if(questionText == null || questionText.isEmpty() || questionText.trim().isEmpty())
                continue;

            var q = new ChapitreQuizzQuestion();
            q.text = questionText;
            var a = answers[i].split(",");
            var b = correctAnswers[i].split(",");
            for(var j=0; j<a.length; j++)
            {
                String answerText = a[j];
                if(answerText == null || answerText.isEmpty() || answerText.trim().isEmpty())
                    continue;

                var aa = new ChapitreQuizzAnswer();
                aa.setText(answerText);
                aa.setIsCorrect(Boolean.parseBoolean(b[j]));
                q.answers.add(aa);
            }
            arr.add(q);
        }
        return arr;
    }

    @Override
    public String toString() {
        return "ChapitreQuizz{" +
                "id=" + id +
                ", questions=" + questions +
                ", chapitreId=" + chapitreId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ChapitreQuizzQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<ChapitreQuizzQuestion> questions) {
        this.questions = questions;
    }

    public int getChapitreId() {
        return chapitreId;
    }

    public void setChapitreId(int chapitreId) {
        this.chapitreId = chapitreId;
    }

}
