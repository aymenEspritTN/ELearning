package com.deltaVelorum.coursify.chapitre.entities;


public class ChapitreQuizzAnswer {
    private String text;
    private boolean isCorrect;

    @Override
    public String toString() {
        return "ChapitreQuizzAnswer{" +
                "text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }

    public ChapitreQuizzAnswer()
    {

    }
    public ChapitreQuizzAnswer(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean correct) {
        isCorrect = correct;
    }
}
