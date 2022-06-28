package de.hochschulestralsund.quizapp.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
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

import java.util.List;

import de.hochschulestralsund.quizapp.Adapter.ScoreAdapter;
import de.hochschulestralsund.quizapp.Database.AppDatabase;
import de.hochschulestralsund.quizapp.Database.Bestenliste;
import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.R;

public class HighscoreActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    private Button btnEasy;
    private Button btnMedium;
    private Button btnHard;
    private Button btnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_highscore_activity);
        selectCategorySpinner = findViewById(R.id.spinnerCategory);
        recyclerView = findViewById(R.id.rcScoreItems);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        database = AppDatabase.getDatabase(getApplicationContext());
        ArrayAdapter categoryAdapter = new ArrayAdapter<>(this, R.layout.spinner_selected_item, Category.values());
        categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        selectCategorySpinner.setAdapter(categoryAdapter);
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
            btnStart = findViewById(R.id.btnStart);
            btnStart.setText("Retry");
        }

        btnEasy = findViewById(R.id.btnEasy);
        btnMedium = findViewById(R.id.btnMedium);
        btnHard = findViewById(R.id.btnHard);
        btnMedium.setBackgroundColor(Color.GRAY);
        btnHard.setBackgroundColor(Color.GRAY);

        List<Bestenliste> bestenliste1 = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(selectCategorySpinner.getSelectedItem().toString(), "easy");

        mAdapter = new ScoreAdapter(bestenliste1);
        recyclerView.setAdapter(mAdapter);
    }

    public void zurueck(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void easy(View view){
        view.setBackgroundColor(fetchcolorOnPrimary());
        btnMedium.setBackgroundColor(Color.GRAY);
        btnHard.setBackgroundColor(Color.GRAY);
        selectedDifficulty = "easy";
        updateDatabaseClick(selectedDifficulty);
    }

    public void medium(View view){
        view.setBackgroundColor(fetchcolorOnPrimary());
        btnEasy.setBackgroundColor(Color.GRAY);
        btnHard.setBackgroundColor(Color.GRAY);
        selectedDifficulty = "medium";
        updateDatabaseClick(selectedDifficulty);
    }

    public void hard(View view){
        view.setBackgroundColor(fetchcolorOnPrimary());
        btnEasy.setBackgroundColor(Color.GRAY);
        btnMedium.setBackgroundColor(Color.GRAY);
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

    //method to get colorOnPrimary
    private int fetchcolorOnPrimary() {
        TypedValue typedValue = new TypedValue();
        TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[] { com.google.android.material.R.attr.colorPrimary });
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }
}
