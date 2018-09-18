package com.example.cameronrichardsonit.additionquizzer;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final String TAG = MainActivity.class.getSimpleName();

    private TextView questionPrompt;
    private RadioGroup answerButtons;
    private Button submitButton;
    private Button nextButton;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionPrompt = findViewById(R.id.questionPrompt);
        answerButtons = findViewById(R.id.radioGroup);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

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
            ((RadioButton) answerButtons.getChildAt(i)).setText(question.getAnswers().get(i) + "");
        }
    }

    private void enableSubmitIfDisabled() {
        if (!submitButton.isEnabled())
            submitButton.setEnabled(true);
    }

    private void checkAnswer() {
        Button selectedAnswerButton = getSelectedRadioButton(answerButtons);
        int selectedAnswer = Integer.parseInt(selectedAnswerButton.getText().toString());
        if (selectedAnswer == question.getCorrectAnswer()) {
            Toast.makeText(this, "Good job! That's correct!", Toast.LENGTH_LONG).show();
        } else Toast.makeText(this, "Sorry, that's incorrect.", Toast.LENGTH_LONG).show();

        markAnswers();
        submitButton.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.VISIBLE);
    }

    private void markAnswers() {

        ArrayList<RadioButton> choices = getRadioButtons(answerButtons);
        for (RadioButton choice : choices) {
            int choiceNum = Integer.parseInt(choice.getText().toString());
            if (choiceNum == question.getCorrectAnswer()) {
                choice.setTextColor(Color.GREEN);
            } else {
                choice.setTextColor(Color.RED);
            }
        }
    }

    private void resetView() {
        ArrayList<RadioButton> choices = getRadioButtons(answerButtons);
        for (RadioButton choice : choices) {
            choice.setTextColor(Color.BLACK);
        }
        answerButtons.clearCheck();
        submitButton.setVisibility(View.VISIBLE);
        submitButton.setEnabled(false);
        nextButton.setVisibility(View.INVISIBLE);
    }

    private ArrayList<RadioButton> getRadioButtons(RadioGroup group) {
        ArrayList<RadioButton> buttons = new ArrayList<>();
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton button = ((RadioButton) group.getChildAt(i));
            buttons.add(button);
        }
        return buttons;
    }

    private RadioButton getSelectedRadioButton(RadioGroup group) {
        int id = answerButtons.getCheckedRadioButtonId();
        return (RadioButton) answerButtons.findViewById(id);
    }
}
