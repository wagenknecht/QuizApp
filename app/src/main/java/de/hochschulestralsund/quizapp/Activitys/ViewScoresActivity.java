package de.hochschulestralsund.quizapp.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hochschulestralsund.quizapp.Adapter.ScoreAdapter;
import de.hochschulestralsund.quizapp.R;

public class ViewScoresActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_highscore_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> easy = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            easy.add("easy" + i);
        }// define an adapter
        mAdapter = new ScoreAdapter(easy);
        recyclerView.setAdapter(mAdapter);
    }

    public void zurueck(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void easy(View view){
        List<String> easy = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            easy.add("easy" + i);
        }// define an adapter
        mAdapter = new ScoreAdapter(easy);
        recyclerView.setAdapter(mAdapter);
    }

    public void medium(View view){
        List<String> medium = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            medium.add("medium" + i);
        }
        mAdapter = new ScoreAdapter(medium);
        recyclerView.setAdapter(mAdapter);
    }

    public void hard(View view){
        List<String> hard = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            hard.add("hard" + i);
        }
        mAdapter = new ScoreAdapter(hard);
        recyclerView.setAdapter(mAdapter);
    }
}
