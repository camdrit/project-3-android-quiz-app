package com.example.cameronrichardsonit.additionquizzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

class Question {
    private final Random random = new Random();
    private final ArrayList<Integer> answers = new ArrayList<>(Arrays.asList( new Integer[3]));
    private int[] addends = new int[2];
    private int correctAnswer = 0;

    public void generateQuestion() {
        int min = 5;
        int max = 50;
        addends = new int[] { random.nextInt(max + 1 - min) + min, random.nextInt(max + 1 - min) + min };
        correctAnswer = addends[0] + addends[1];
        answers.set(0, correctAnswer);
        answers.set(1, correctAnswer - random.nextInt(14 + 1 - min) + min);
        answers.set(2, correctAnswer + random.nextInt(12 + 1 - min) + min);
        Collections.shuffle(answers);
    }

    public String getQuestion() {
        return addends[0] + " + " + addends[1] + "?";
    }

    public ArrayList<Integer> getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
