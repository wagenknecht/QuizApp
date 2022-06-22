package de.hochschulestralsund.quizapp.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hochschulestralsund.quizapp.Api.OpenTrivialServiceEndless;
import de.hochschulestralsund.quizapp.Api.QuestionResponseCallback;
import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.Entities.Question;
import de.hochschulestralsund.quizapp.R;

public class EndlessQuizActivity extends AppCompatActivity {

    private Button correctAnswere;
    private int score;
    private int lives = 3;
    private int number;
    private List<Question> question;
    List<Button> buttons = new ArrayList<>();
    private Button btnContinue;
    private int questionsPerApiCall = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_endless_activity);
        buttons.add(findViewById(R.id.antwort1));
        buttons.add(findViewById(R.id.antwort2));
        buttons.add(findViewById(R.id.antwort3));
        buttons.add(findViewById(R.id.antwort4));
        //get Questions
        if (getIntent().getExtras() != null) {
            question = (List<Question>) getIntent().getSerializableExtra("question");
        }
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setClickable(false);
        setAnsweres();
        setQuestion();
        number = 0;
    }

    //gets Triggert when a Button is clicked
    public void answere(View view) {
        //check if questions are all asked
        if (number % questionsPerApiCall == 0) {
            loadMoreQuestions();
        }
        btnContinue.setClickable(true);
        correctAnswere.setBackgroundColor(Color.GREEN);
        buttons.forEach(a -> a.setClickable(false));
        //if question is correct
        if (view.getId() == correctAnswere.getId()) {
            score++;
        } else {
            lives--;
            ImageView imageView;
            switch (lives) {
                case 2:
                    imageView = findViewById(R.id.lives3);
                    imageView.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    imageView = findViewById(R.id.lives2);
                    imageView.setVisibility(View.INVISIBLE);
                    break;
                case 0:
                    imageView = findViewById(R.id.lives1);
                    imageView.setVisibility(View.INVISIBLE);
                    break;
            }
            view.setBackgroundColor(Color.RED);
        }
    }

    public void setQuestion() {
        TextView questionTitel = findViewById(R.id.frageTitelNummer);
        TextView Question = findViewById(R.id.Frage);
        questionTitel.setText(question.get(number % questionsPerApiCall).getCategory());
        Question.setText(question.get(number % questionsPerApiCall).getQuestion());
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
        correctAnswere.setText("correct: " + question.get(number % questionsPerApiCall).getCorrect_answer());
        int j = 0;
        for (int i = 0; i < buttons.size(); i++) {
            if (i != correct) {
                Button button = buttons.get(i);
                button.setText(question.get(number % questionsPerApiCall).getIncorrect_answers().get(j));
                j++;
            }
        }
        return correctAnswere;
    }

    public void clickContinue(View view) {
        if(lives == 0){
            Intent intent = new Intent(this, HighscoreActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        } else {
                buttons.forEach(a -> a.setClickable(true));
                number++;
                setAnsweres();
                setQuestion();
            }
            view.setClickable(false);
            buttons.forEach(a -> a.setBackgroundColor(Color.GRAY));

    }

    public void loadMoreQuestions() {
        OpenTrivialServiceEndless openTrivialService = new OpenTrivialServiceEndless();
        Category category = (Category) getIntent().getSerializableExtra("category");
        openTrivialService.getQuestions(questionsPerApiCall, category, new QuestionResponseCallback() {

            @Override
            public void onQuestionResponse(List<Question> questionList) {
                question = questionList;
            }
        });

    }


}