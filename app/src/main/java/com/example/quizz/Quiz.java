package com.example.quizz;

import java.io.Serializable;
import java.util.List;

public class Quiz implements Serializable {
    public String title;
    public String createdBy;
    public List<Question> questions;

    public Quiz() {}

    public Quiz(String title, String createdBy, List<Question> questions) {
        this.title = title;
        this.createdBy = createdBy;
        this.questions = questions;
    }
}
