package de.hochschulestralsund.quizapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.hochschulestralsund.quizapp.Activitys.HighscoreActivity;
import de.hochschulestralsund.quizapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void score(View view){
        Intent intent = new Intent(this, HighscoreActivity.class);
        startActivity(intent);
    }
}