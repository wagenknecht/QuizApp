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
import android.widget.Button;
import android.widget.Spinner;

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
import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.R;

public class ViewScoresActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase database;
    private Spinner selectCategorySpinner;
    private int score;
    private String category;
    private String difficulty;
    //default easy
    private String selectedDifficulty = "easy";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_highscore_activity);
        selectCategorySpinner = findViewById(R.id.spinnerCategory);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        database = AppDatabase.getDatabase(getApplicationContext());
        selectCategorySpinner.setAdapter(new ArrayAdapter<Category>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Category.values()));
        selectCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Bestenliste> bestenliste = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(selectCategorySpinner.getSelectedItem().toString(), selectedDifficulty);
                mAdapter = new ScoreAdapter(bestenliste);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (getIntent().getExtras() != null) {
            score = (Integer) getIntent().getSerializableExtra("score");
            category = (String) getIntent().getSerializableExtra("category");
            difficulty = (String) getIntent().getSerializableExtra("difficulty");
            //        if (score>=DatabaseHighsore)
            checkScore();
        }

        List<Bestenliste> bestenliste1 = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(selectCategorySpinner.getSelectedItem().toString(), "easy");

        mAdapter = new ScoreAdapter(bestenliste1);
        recyclerView.setAdapter(mAdapter);
    }

    public void zurueck(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void easy(View view){
        selectedDifficulty = "easy";
        updateDatabaseClick(selectedDifficulty);
    }

    public void medium(View view){
        selectedDifficulty = "medium";
        updateDatabaseClick(selectedDifficulty);
    }

    public void hard(View view){
        selectedDifficulty = "hard";
        updateDatabaseClick(selectedDifficulty);
    }

    private void updateDatabaseClick(String difficulty)  {
        String spinnerItem = selectCategorySpinner.getSelectedItem().toString();
        List<Bestenliste> bestenliste = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(spinnerItem, difficulty);
        mAdapter = new ScoreAdapter(bestenliste);
        recyclerView.setAdapter(mAdapter);
    }

    public void newHighscore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                ViewScoresActivity.this
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

    private void checkScore() {
        List<Bestenliste> bestenliste = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(category, difficulty);

        if (bestenliste.size() == 10) {

            Bestenliste bestenliste1 = bestenliste.get(9);
            if(bestenliste1.getScore() < score)   {
                newHighscore();

                database.bestenlisteDao().removeBestenlisteEintrag(bestenliste1);
                updateDatabase();
            } else  {
                updateDatabase();
            }

        }else   {
            newHighscore();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
