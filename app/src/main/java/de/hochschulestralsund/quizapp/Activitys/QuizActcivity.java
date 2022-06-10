package de.hochschulestralsund.quizapp.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hochschulestralsund.quizapp.R;

public class QuizActcivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
    }

    public void antwort1(View view){
        Intent intent = new Intent(this,HighscoreActivity.class);
        startActivity(intent);
    }
}
