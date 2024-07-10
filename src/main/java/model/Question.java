package model;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class Question {
    private int id;
    private String question;
    private List<Integer> answers;
    @Getter
    private boolean failed;
    @Getter
    private boolean success;


}
