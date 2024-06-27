package model;

import java.util.List;

public class Question {
    private int id;
    private String question;
    private List<Integer> answers;
    private boolean failed;
    private boolean success;


    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean sucsess) {
        this.success = sucsess;
    }
}
