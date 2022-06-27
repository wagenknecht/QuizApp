package de.hochschulestralsund.quizapp.Activitys;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hochschulestralsund.quizapp.Entities.Question;
import de.hochschulestralsund.quizapp.R;

public class QuizActivity extends AppCompatActivity {

    private Button correctAnswere;
    private int score;
    private List<Question> question;
    private int number;
    List<Button> buttons = new ArrayList<>();
    private Button btnContinue;
    private String category;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        buttons.add(findViewById(R.id.antwort1));
        buttons.add(findViewById(R.id.antwort2));
        buttons.add(findViewById(R.id.antwort3));
        buttons.add(findViewById(R.id.antwort4));
        //get Questions
        if (getIntent().getExtras() != null) {
            question = (List<Question>) getIntent().getSerializableExtra("question");
        }
        category = question.get(0).getCategory();
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setVisibility(View.INVISIBLE);
        setAnsweres();
        setQuestion();
        number = 0;
    }

    //gets Triggert when a Button is clicked
    public void answere(View view) {
        btnContinue.setVisibility(View.VISIBLE);
        correctAnswere.setBackgroundColor(Color.GREEN);
        buttons.forEach(a -> a.setClickable(false));
        //if question is correct
        if (view.getId() == correctAnswere.getId()) {
            score = score + 1;
        } else {
            view.setBackgroundColor(Color.RED);
        }
    }

    public void setQuestion() {
        TextView questionTitel = findViewById(R.id.frageTitelNummer);
        TextView Question = findViewById(R.id.Frage);
        questionTitel.setText(question.get(number).getCategory());
        Question.setText(question.get(number).getQuestion());
        TextView Number = findViewById(R.id.QuestionNumber);
        int display = number + 1;
        Number.setText("Question Number: " + display);
    }

    public Button setAnsweres() {
        //select a Random button to be right
        Random random = new Random();
        int correct = random.nextInt(4);
        correctAnswere = buttons.get(correct); //save CorrectAnswer to global string to compare result
        //todo remove string correct, just for testing
        correctAnswere.setText("correct: " + question.get(number).getCorrect_answer());
        int j = 0;
        for (int i = 0; i < buttons.size(); i++) {
            if (i != correct) {
                Button button = buttons.get(i);
                button.setText(question.get(number).getIncorrect_answers().get(j));
                j++;
            }
        }
        return correctAnswere;
    }

    public void clickContinue(View view) {

        //check if all questions are answered/finish
        if (number == 9) {
            Intent intent = new Intent(this, ViewScoresActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("category", category);
            startActivity(intent);
        } else {
            buttons.forEach(a -> a.setClickable(true));
            number++;
            setAnsweres();
            setQuestion();
        }
        view.setVisibility(View.INVISIBLE);
        buttons.forEach(a -> a.setBackgroundColor(fetchcolorOnPrimary()));

    }

    //method to get primaryColor
    private int fetchcolorOnPrimary() {

        TypedValue typedValue = new TypedValue();

        TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[] { com.google.android.material.R.attr.colorOnPrimary });
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

}
