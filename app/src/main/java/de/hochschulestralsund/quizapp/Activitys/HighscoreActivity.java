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

import de.hochschulestralsund.quizapp.Adapter.ScoreAdapter;
import de.hochschulestralsund.quizapp.Database.AppDatabase;
import de.hochschulestralsund.quizapp.Database.Bestenliste;
import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.Entities.Difficulty;
import de.hochschulestralsund.quizapp.R;

public class HighscoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    int score;
    private String category;
    private String difficulty;
    private AppDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_activity);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        database = AppDatabase.getDatabase(getApplicationContext());
        //List<Bestenliste> bestenliste = database.bestenlisteDao().getAllBestenlisteEintraege();
        /*List<String> input = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            input.add(String.valueOf(100-i));
        }// define an adapter*/
        //mAdapter = new ScoreAdapter(bestenliste);
        //recyclerView.setAdapter(mAdapter);



        if (getIntent().getExtras() != null) {
            score = (Integer) getIntent().getSerializableExtra("score");
            category = getIntent().getStringExtra("category");
            difficulty = getIntent().getStringExtra("difficulty");
        }
       //if (score>=bestenliste.get())
        List<Bestenliste> bestenliste = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(category, difficulty);
        addNewHighscore();

        mAdapter = new ScoreAdapter(bestenliste);
        recyclerView.setAdapter(mAdapter);
    }

    public void zurueck(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void retry(View view){
        Intent intent = new Intent(this, StartQuizActivity.class);
        startActivity(intent);
    }

    public void newHighscore(){
        AlertDialog.Builder builder = new AlertDialog.Builder(
                HighscoreActivity.this
        );
        builder.setTitle("\uD83C\uDF89 new high score: "+score+" points \uD83C\uDF89");
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

                Bestenliste newEntry = new Bestenliste(input.getText().toString(), category, difficulty, score);
                database.bestenlisteDao().addSpieler(newEntry);
                database.bestenlisteDao().updateBestenliste(newEntry);
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

    private void updateDatabase()   {
        List<Bestenliste> bestenliste = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(category, difficulty);
        mAdapter = new ScoreAdapter(bestenliste);
        recyclerView.setAdapter(mAdapter);
    }

    private void addNewHighscore() {
        List<Bestenliste> bestenliste = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(category, difficulty);

        int lowestScore = 10;
        Bestenliste entryWithLowestScore = null;
        if (bestenliste.size() >= 10) {
            Iterator<Bestenliste> iterator = bestenliste.iterator();

            while (iterator.hasNext()) {
                Bestenliste entry = iterator.next();
                int scoreBestenliste = entry.getScore();
                //search lowest score
                if(scoreBestenliste < lowestScore)  {
                    lowestScore = scoreBestenliste;
                    entryWithLowestScore = entry;
                }

            }

            if(lowestScore < score)    {
                newHighscore();

                database.bestenlisteDao().removeBestenlisteEintrag(entryWithLowestScore);
                updateDatabase();
            }

        }else   {
            newHighscore();
        }
    }

}