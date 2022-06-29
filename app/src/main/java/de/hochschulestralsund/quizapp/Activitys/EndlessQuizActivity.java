package de.hochschulestralsund.quizapp.Activitys;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hochschulestralsund.quizapp.Api.OpenTrivialService;
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
    private String category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_endless_activity);
        buttons.add(findViewById(R.id.antwort1));
        buttons.add(findViewById(R.id.antwort2));
        buttons.add(findViewById(R.id.antwort3));
        buttons.add(findViewById(R.id.antwort4));
        //get Questions from intent
        if (getIntent().getExtras() != null) {
            question = (List<Question>) getIntent().getSerializableExtra("question");
        }
        //get the Category
        category = question.get(0).getCategory();
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setVisibility(View.INVISIBLE);
        setAnswers();
        setQuestion();
        number = 0;
    }

    //gets Triggert when a Button is clicked
    public void answer(View view) {
        //check if questions are all asked, fetch new if
        if (number % questionsPerApiCall == 0) {
            loadMoreQuestions();
        }
        btnContinue.setVisibility(View.VISIBLE); //creates a Button when the question is answered
        buttons.forEach(a -> {
            a.setClickable(false);
            a.setBackgroundColor(Color.GRAY);
        });
        correctAnswere.setBackgroundColor(Color.GREEN); //paints the correct answer green
        //if question is correct
        if (view.getId() == correctAnswere.getId()) {
            score++;
            TextView tvScore = findViewById(R.id.ScoreNumber);
            tvScore.setText("Score: " + score);
        } else {
            lives--;
            ImageView imageView;
            switch (lives) { //removes the lives
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

    //set the question and the Titel, fetched from API in the onStart methode
    public void setQuestion() {
        TextView questionTitel = findViewById(R.id.frageTitelNummer);
        TextView Question = findViewById(R.id.Frage);
        questionTitel.setText(question.get(number % questionsPerApiCall).getCategory());
        Question.setText(question.get(number % questionsPerApiCall).getQuestion());

    }

    public Button setAnswers() {
        //select a Random button to be right
        Random random = new Random();
        int correct = random.nextInt(4);
        correctAnswere = buttons.get(correct); //save CorrectAnswer to global string to compare result
        correctAnswere.setText(question.get(number % questionsPerApiCall).getCorrect_answer());
        //assign the wrong answers to a button
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
        //when the the user has used all trys change to Highscore
        if (lives == 0) {
            Intent intent = new Intent(this, EndlessHighsoreActivity.class);
            intent.putExtra("score", score); //add the score and the Category to the intent, needed for Highscore
            intent.putExtra("category", category);
            startActivity(intent);
        } else { //assign the Answers and Questions new
            buttons.forEach(a -> a.setClickable(true));
            number++;
            setAnswers();
            setQuestion();
        }
        view.setVisibility(View.INVISIBLE);
        buttons.forEach(a -> a.setBackgroundColor(fetchcolorOnPrimary()));
    }

    //load more questions from the API
    public void loadMoreQuestions() {
        OpenTrivialService openTrivialService = new OpenTrivialService();
        Category category = (Category) getIntent().getSerializableExtra("category"); //get Category from intent
        openTrivialService.getQuestions(questionsPerApiCall, category, new QuestionResponseCallback() {

            @Override
            public void onQuestionResponse(List<Question> questionList) {
                question = questionList;
            }
        });
    }

    //method to get colorOnPrimary
    private int fetchcolorOnPrimary() {
        TypedValue typedValue = new TypedValue();
        TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[]{com.google.android.material.R.attr.colorOnPrimary});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }
}