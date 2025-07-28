package com.example.quizz;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    public String question;
    public List<String> options;
    public int correctIndex;

    public Question() {} // Firestore needs this

    public Question(String question, List<String> options, int correctIndex) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }
}
