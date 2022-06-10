package de.hochschulestralsund.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button highscoreButton;
    Button settingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startBtn);
        highscoreButton = findViewById(R.id.highscoreBtn);
        settingButton = findViewById(R.id.settingsBtn);

        startButton.setOnClickListener(view -> {
            Intent intent_startQuiz = new Intent(MainActivity.this, startQuizActivity.class);
            startActivity(intent_startQuiz);
        });

        highscoreButton.setOnClickListener(view -> {

        });

        settingButton.setOnClickListener(view -> {

        });
    }
}