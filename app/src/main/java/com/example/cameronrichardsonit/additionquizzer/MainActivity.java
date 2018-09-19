package com.example.cameronrichardsonit.additionquizzer;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView questionPrompt;
    private RadioGroup answerButtons;
    private Button submitButton, nextButton;
    private Question question;

    private ColorStateList incorrectAnswerColors, correctAnswerColors, defaultAnswerColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionPrompt = findViewById(R.id.questionPrompt);
        answerButtons = findViewById(R.id.radioGroup);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

        incorrectAnswerColors = new ColorStateList(
                new int[][]{
                        new int[] { android.R.attr.state_enabled }
                },
                new int[] { getResources().getColor(R.color.colorAccent) }
        );

        correctAnswerColors = new ColorStateList(
                new int[][]{
                        new int[] { android.R.attr.state_enabled }
                },
                new int[] { getResources().getColor(R.color.correctAnswerColor) }
        );

        defaultAnswerColors = new ColorStateList(
                new int[][]{
                        new int[] { android.R.attr.state_enabled }
                },
                new int[] { getResources().getColor(R.color.colorPrimaryText) }
        );

        question = new Question();

        answerButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                enableSubmitIfDisabled();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupQuestion();
            }
        });

        setupQuestion();

    }

    private void setupQuestion() {
        resetView();
        question.generateQuestion();
        questionPrompt.setText(question.getQuestion());

        for (int i = 0; i < answerButtons.getChildCount(); i++) {
            ((AppCompatRadioButton) answerButtons.getChildAt(i)).setText(question.getAnswers().get(i).toString());
        }
    }

    private void enableSubmitIfDisabled() {
        if (!submitButton.isEnabled())
            submitButton.setEnabled(true);
    }

    private void checkAnswer() {
        Button selectedAnswerButton = getSelectedRadioButton();
        int selectedAnswer = Integer.parseInt(selectedAnswerButton.getText().toString());
        if (selectedAnswer == question.getCorrectAnswer()) {
            Toast.makeText(this, "Good job! That's correct!", Toast.LENGTH_LONG).show();
        } else Toast.makeText(this, "Sorry, that's incorrect.", Toast.LENGTH_LONG).show();

        markAnswers();
        submitButton.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.VISIBLE);
    }

    @SuppressLint("RestrictedApi")
    private void markAnswers() {

        ArrayList<AppCompatRadioButton> choices = getRadioButtons(answerButtons);
        for (AppCompatRadioButton choice : choices) {
            int choiceNum = Integer.parseInt(choice.getText().toString());
            if (choiceNum == question.getCorrectAnswer()) {
                choice.setSupportButtonTintList(correctAnswerColors);

            } else if (choice.isChecked()) {
                choice.setSupportButtonTintList(incorrectAnswerColors);
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void resetView() {
        ArrayList<AppCompatRadioButton> choices = getRadioButtons(answerButtons);
        for (AppCompatRadioButton choice : choices) {
            choice.setSupportButtonTintList(defaultAnswerColors);

        }
        answerButtons.clearCheck();
        submitButton.setVisibility(View.VISIBLE);
        submitButton.setEnabled(false);
        nextButton.setVisibility(View.INVISIBLE);
    }

    private ArrayList<AppCompatRadioButton> getRadioButtons(RadioGroup group) {
        ArrayList<AppCompatRadioButton> buttons = new ArrayList<>();
        for (int i = 0; i < group.getChildCount(); i++) {
            AppCompatRadioButton button = ((AppCompatRadioButton) group.getChildAt(i));
            buttons.add(button);
        }
        return buttons;
    }

    private AppCompatRadioButton getSelectedRadioButton() {
        int id = answerButtons.getCheckedRadioButtonId();
        return (AppCompatRadioButton) answerButtons.findViewById(id);
    }
}
