package de.hochschulestralsund.quizapp.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hochschulestralsund.quizapp.Adapter.EndlessScoreAdapter;
import de.hochschulestralsund.quizapp.Adapter.ScoreAdapter;
import de.hochschulestralsund.quizapp.Database.AppDatabase;
import de.hochschulestralsund.quizapp.Database.Bestenliste;
import de.hochschulestralsund.quizapp.Database.EndlessHighscore;
import de.hochschulestralsund.quizapp.R;

public class EndlessHighsoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    int score;
    private AppDatabase database;
    private String category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endless_highscore_activity);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        database = AppDatabase.getDatabase(getApplicationContext());
        List<EndlessHighscore> endlessHighscore = database.endlessHighscoreDao().getEndlessHighscoreCategoryEntry(category);
        /*List<String> input = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            input.add(String.valueOf(100-i));
        }// define an adapter*/
        mAdapter = new EndlessScoreAdapter(endlessHighscore);
        recyclerView.setAdapter(mAdapter);

        if (getIntent().getExtras() != null) {
            score = (Integer) getIntent().getSerializableExtra("score");
            category = (String) getIntent().getSerializableExtra("category");
            //        if (score>=DatabaseHighsore)
            addNewHighscore();
        }

    }

    public void zurueck(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void retry(View view) {
        Intent intent = new Intent(this, StartEndlessActivity.class);
        startActivity(intent);
    }

    public void newHighscore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                EndlessHighsoreActivity.this
        );
        builder.setTitle("\uD83C\uDF89 new high score: " + score + " points in " + category +  "\uD83C\uDF89");
        builder.setCancelable(false);
        builder.setMessage("Please enter a name under which the high score should be saved");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringBuilder stringBuilder = new StringBuilder();
                System.out.println(input.getText().toString());
                //todo add to DB, reload page after insert to display new item
                EndlessHighscore newEntry = new EndlessHighscore(input.getText().toString(), category, score);
                database.endlessHighscoreDao().addSpieler(newEntry);
                database.endlessHighscoreDao().updateEndlessHighscore(newEntry);
                updateDatabase();
            }
        });

        builder.setNegativeButton("don't save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void addNewHighscore() {
        List<EndlessHighscore> bestenliste = database.endlessHighscoreDao().getEndlessHighscoreCategoryEntry(category);
        int lowestScore = 10;
        EndlessHighscore entryWithLowestScore = null;
        if (bestenliste.size() >= 10) {
            Iterator<EndlessHighscore> iterator = bestenliste.iterator();

            while (iterator.hasNext()) {
                EndlessHighscore entry = iterator.next();
                int scoreBestenliste = entry.getScore();
                //search lowest score
                if(scoreBestenliste < lowestScore)  {
                    lowestScore = scoreBestenliste;
                    entryWithLowestScore = entry;
                }

            }

            if(lowestScore < score)    {
                newHighscore();

                database.endlessHighscoreDao().removeEndlessHighscoreEntry(entryWithLowestScore);
                updateDatabase();
            }

        }else   {
            newHighscore();
        }
    }

    private void updateDatabase()   {
        List<EndlessHighscore> bestenliste = database.endlessHighscoreDao().getEndlessHighscoreCategoryEntry(category);
        mAdapter = new EndlessScoreAdapter(bestenliste);
        recyclerView.setAdapter(mAdapter);
    }
}