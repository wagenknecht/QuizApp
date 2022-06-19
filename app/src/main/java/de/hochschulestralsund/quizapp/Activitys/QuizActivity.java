package de.hochschulestralsund.quizapp.Activitys;

import android.content.Intent;
import android.os.Bundle;
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
    private int lives =3;
    private List<Question> question;
    private int number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        //get Questions
        if(getIntent().getExtras() != null) {
            question = (List<Question>) getIntent().getSerializableExtra("question");
        }
        correctAnswere = setAnsweres();
        setQuestion();
        number=0;
    }

    //gets Triggert when a Button is clicked
    public void answere(View view){
        System.out.println(number);
        //if question is correct
        if (view.getId()==correctAnswere.getId()){
            score=score+1;
            //check if all questions are answered/finish
            if (number==10){
                Intent intent=new Intent(this, HighscoreActivity.class);
                intent.putExtra("score",score);
                startActivity(intent);
            }
            else{
                number=number+1;
                setCorrect();
                setAnsweres();
                setQuestion();
            }
        }
        else{
            Intent intent=new Intent(this, HighscoreActivity.class);
            intent.putExtra("score",score);
            startActivity(intent);
        }
    }

    public void setQuestion(){
        TextView questionTitel = findViewById(R.id.frageTitelNummer);
        TextView Question = findViewById(R.id.Frage);
        questionTitel.setText(question.get(number).getCategory());
        Question.setText(question.get(number).getQuestion());
        TextView Number = findViewById(R.id.QuestionNumber);
        Number.setText("Question Number: "+number);
    }

    public int setCorrect(){
        Random random = new Random();
        int correct = random.nextInt(4);
        return correct;
    }

    public Button setAnsweres(){
        int correct = setCorrect();
        List<Button>buttons=new ArrayList<>();
        buttons.add(findViewById(R.id.antwort1));
        buttons.add(findViewById(R.id.antwort2));
        buttons.add(findViewById(R.id.antwort3));
        buttons.add(findViewById(R.id.antwort4));
        Button correctButton = buttons.get(correct);
        correctButton.setText("correct"+question.get(number).getCorrect_answer());
        int j = 0;
        for (int i =0;i<buttons.size();i++){
            if (i!=correct){
               Button button = buttons.get(i);
               button.setText(question.get(number).getIncorrect_answers().get(j));
               j++;
            }
        }
        return correctButton;
    }
}
