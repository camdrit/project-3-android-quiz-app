package com.example.cameronrichardsonit.additionquizzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Question {
    private final Random random = new Random();
    private ArrayList<Integer> answers = new ArrayList<>(Arrays.asList( new Integer[3]));
    private int[] addends = new int[2];
    private int correctAnswer = 0;

    public void generateQuestion() {
        addends = new int[] { random.nextInt(100), random.nextInt(100) };
        correctAnswer = addends[0] + addends[1];
        answers.set(0, correctAnswer);
        answers.set(1, correctAnswer - random.nextInt(14));
        answers.set(2, correctAnswer + random.nextInt(12));
        Collections.shuffle(answers);
    }

    public String getQuestion() {
        return "What is " + addends[0] + " + " + addends[1] + "?";
    }

    public ArrayList<Integer> getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
