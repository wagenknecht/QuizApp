package de.hochschulestralsund.quizapp.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hochschulestralsund.quizapp.Adapter.EndlessScoreAdapter;
import de.hochschulestralsund.quizapp.Adapter.ScoreAdapter;
import de.hochschulestralsund.quizapp.Database.AppDatabase;
import de.hochschulestralsund.quizapp.Database.Bestenliste;
import de.hochschulestralsund.quizapp.Database.EndlessHighscore;
import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.R;

public class EndlessHighsoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner selectCategorySpinner;
    int score;
    private AppDatabase database;
    private String category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endless_highscore_activity);
        recyclerView = findViewById(R.id.recyclerView);
        selectCategorySpinner = findViewById(R.id.spinnerCategory);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        database = AppDatabase.getDatabase(getApplicationContext());
        selectCategorySpinner.setAdapter(new ArrayAdapter<Category>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Category.values()));
        selectCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<EndlessHighscore> endlessHighscores = database.endlessHighscoreDao().getEndlessHighscoreCategoryEntry(selectCategorySpinner.getSelectedItem().toString());
                mAdapter = new EndlessScoreAdapter(endlessHighscores);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (getIntent().getExtras() != null) {
            score = (Integer) getIntent().getSerializableExtra("score");
            category = (String) getIntent().getSerializableExtra("category");
            //        if (score>=DatabaseHighsore)
            checkScore();
        }

        List<EndlessHighscore> endlessHighscore = database.endlessHighscoreDao().getEndlessHighscoreCategoryEntry(selectCategorySpinner.getSelectedItem().toString());
        mAdapter = new EndlessScoreAdapter(endlessHighscore);
        recyclerView.setAdapter(mAdapter);

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
                //add new entry
                EndlessHighscore newEntry = new EndlessHighscore(input.getText().toString(), category, score);
                database.endlessHighscoreDao().addSpieler(newEntry);
                database.endlessHighscoreDao().updateEndlessHighscore(newEntry);
                //reload DB
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
    // checks the score if it is greater than the smallest value in the list or
    // there are less than 10 entries in the db, then the score can be saved
    private void checkScore() {
        List<EndlessHighscore> bestenliste = database.endlessHighscoreDao().getEndlessHighscoreCategoryEntry(category);

        if (bestenliste.size() == 10) {

            EndlessHighscore bestenliste1 = bestenliste.get(9);
            if(bestenliste1.getScore() < score)   {
                newHighscore();

                database.endlessHighscoreDao().removeEndlessHighscoreEntry(bestenliste1);
                updateDatabase();
            } else  {
                updateDatabase();
            }

        }else   {
            newHighscore();
        }
    }

    //updating the adapter
    private void updateDatabase()   {
        List<EndlessHighscore> bestenliste = database.endlessHighscoreDao().getEndlessHighscoreCategoryEntry(category);
        mAdapter = new EndlessScoreAdapter(bestenliste);
        recyclerView.setAdapter(mAdapter);
    }

}