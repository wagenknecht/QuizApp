package de.hochschulestralsund.quizapp.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.hochschulestralsund.quizapp.Database.AppDatabase;
import androidx.appcompat.app.AppCompatActivity;

import de.hochschulestralsund.quizapp.R;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getDatabase(getApplicationContext());
    }

    public void clickStartQuiz(View view) {
        Intent intent_startQuiz = new Intent(MainActivity.this, StartQuizActivity.class);
        startActivity(intent_startQuiz);
    }

    public void clickHighscore(View view) {
        Intent highscore = new Intent(MainActivity.this, ViewScoresActivity.class);
        startActivity(highscore);
    }

    public void clickHighscoreEndless(View view) {
        Intent highscore = new Intent(MainActivity.this, EndlessHighsoreActivity.class);
        startActivity(highscore);
    }

    public void clickSettings(View view) {
        //add Intent to Settings
    }

    public void endless(View view) {
        Intent highscore = new Intent(MainActivity.this, StartEndlessActivity.class);
        startActivity(highscore);
    }
}