package de.hochschulestralsund.quizapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.hochschulestralsund.quizapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickStartQuiz(View view) {
        Intent intent_startQuiz = new Intent(MainActivity.this, StartQuizActivity.class);
        startActivity(intent_startQuiz);
    }

    public void clickHighscore(View view) {
        Intent highscore = new Intent(MainActivity.this, ViewScoresActivity.class);
        startActivity(highscore);
    }

    public void clickSettings(View view) {
        //add Intent to Settings
    }
}