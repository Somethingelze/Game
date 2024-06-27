package model;

import java.util.List;

public class Data {
    private List<Question> questions;
    private List<Answer> answers;

    // Геттеры и сеттеры
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
