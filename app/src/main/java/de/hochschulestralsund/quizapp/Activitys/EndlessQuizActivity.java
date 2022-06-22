package de.hochschulestralsund.quizapp.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
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
        setAnsweres();
        setQuestion();
        number = 0;
    }

    //gets Triggert when a Button is clicked
    public void answere(View view) {
        //if question is correct
        if (view.getId() == correctAnswere.getId()) {
            score = score + 1;
            number = number + 1;
            setAnsweres();
            setQuestion();
        } else {
            lives = lives - 1;
            if (lives == 2) {
                ImageView imageView = findViewById(R.id.lives3);
                imageView.setVisibility(View.INVISIBLE);
            }
            if (lives == 1) {
                ImageView imageView = findViewById(R.id.lives2);
                imageView.setVisibility(View.INVISIBLE);
            }
            if (lives == 0) {
                Intent intent = new Intent(this, EndlessHighsoreActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
            } else {
                number = number + 1;
                setAnsweres();
                setQuestion();
            }
        }
        if (number == 50) {
            setContentView(R.layout.endless_end_reached_activity);
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

    public void no(View view) {
        Intent intent = new Intent(this, EndlessHighsoreActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }

    public void yes(View view) {
        Intent intent = new Intent(this, EndlessQuizActivity.class);
        OpenTrivialServiceEndless openTrivialService = new OpenTrivialServiceEndless();
        Category category = (Category) getIntent().getSerializableExtra("category");
        openTrivialService.getQuestions(50, category, new QuestionResponseCallback() {
            @Override
            public void onQuestionResponse(List<Question> questionList) {
                questionList.forEach(question -> intent.putExtra("question", (Serializable) questionList));
                startActivity(intent);
            }
        });
        number = 0;
    }
}