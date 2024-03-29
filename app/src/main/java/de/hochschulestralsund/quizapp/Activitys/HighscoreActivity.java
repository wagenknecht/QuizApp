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
import android.widget.Button;
import android.widget.EditText;
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
        Category[] categories= Category.values();
        ArrayAdapter categoryAdapter = new ArrayAdapter<>(this, R.layout.spinner_selected_item, categories);
        categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        selectCategorySpinner.setAdapter(categoryAdapter);

        //when the category is changed, the spinner have to display the values for the selected category
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

        btnEasy = findViewById(R.id.btnEasy);
        btnMedium = findViewById(R.id.btnMedium);
        btnHard = findViewById(R.id.btnHard);
        btnMedium.setBackgroundColor(Color.GRAY);
        btnHard.setBackgroundColor(Color.GRAY);


        if (getIntent().getExtras() != null) {
            score = (Integer) getIntent().getSerializableExtra("score");
            category = (String) getIntent().getSerializableExtra("category");
            difficulty = (String) getIntent().getSerializableExtra("difficulty");

            switch(difficulty){
                case "hard": hard(btnHard); break;
                case "medium": medium(btnMedium); break;
                case "easy": easy(btnEasy); break;
            }

            for(int i=0;i<categories.length;i++){
                if(categories[i].toString().equals(category)){
                    selectCategorySpinner.setSelection(i);
                    break;
                }
            }

            checkScore();
            Button btnStart = findViewById(R.id.btnStart);
            btnStart.setText("Retry");
        }


        List<Bestenliste> bestenliste1 = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(selectCategorySpinner.getSelectedItem().toString(), "easy");

        mAdapter = new ScoreAdapter(bestenliste1);
        recyclerView.setAdapter(mAdapter);
    }

    public void zurueck(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    // gets triggered when button easy is trigged
    public void easy(View view) {
        view.setBackgroundColor(fetchcolorOnPrimary());
        btnMedium.setBackgroundColor(Color.GRAY);
        btnHard.setBackgroundColor(Color.GRAY);
        selectedDifficulty = "easy";
        updateDatabaseClick(selectedDifficulty);
    }
    // gets triggered when button medium is trigged
    public void medium(View view) {
        view.setBackgroundColor(fetchcolorOnPrimary());
        btnEasy.setBackgroundColor(Color.GRAY);
        btnHard.setBackgroundColor(Color.GRAY);
        selectedDifficulty = "medium";
        updateDatabaseClick(selectedDifficulty);
    }
    // gets triggered when button hard is trigged
    public void hard(View view) {
        view.setBackgroundColor(fetchcolorOnPrimary());
        btnEasy.setBackgroundColor(Color.GRAY);
        btnMedium.setBackgroundColor(Color.GRAY);
        selectedDifficulty = "hard";
        updateDatabaseClick(selectedDifficulty);
    }

    //updating the adapter when a button (easy, medium, hard) is clicked
    private void updateDatabaseClick(String difficulty) {
        String spinnerItem = selectCategorySpinner.getSelectedItem().toString();
        List<Bestenliste> bestenliste = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(spinnerItem, difficulty);
        mAdapter = new ScoreAdapter(bestenliste);
        recyclerView.setAdapter(mAdapter);
    }

    public void newHighscore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                HighscoreActivity.this
        );
        builder.setTitle("\uD83C\uDF89 New Highscore: " + score + " points. \uD83C\uDF89");
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
                //add new entry to database
                Bestenliste newEntry = new Bestenliste(input.getText().toString(), category, difficulty, score);
                database.bestenlisteDao().addSpieler(newEntry);
                database.bestenlisteDao().updateBestenliste(newEntry);
                //reload DB and update adapter
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
    //updating the adapter
    private void updateDatabase() {
        List<Bestenliste> bestenliste = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(category, difficulty);
        mAdapter = new ScoreAdapter(bestenliste);
        recyclerView.setAdapter(mAdapter);
    }

    // checks the score if it is greater than the smallest value in the list or
    // there are less than 10 entries in the db, then the score can be saved
    private void checkScore() {
        List<Bestenliste> bestenliste = database.bestenlisteDao().getBestenlisteCategoryDifficultyEntry(category, difficulty);

        if (bestenliste.size() == 10) {

            Bestenliste bestenliste1 = bestenliste.get(9);
            if (bestenliste1.getScore() < score) {
                newHighscore();

                database.bestenlisteDao().removeBestenlisteEintrag(bestenliste1);
                updateDatabase();
            } else {
                updateDatabase();
            }

        } else {
            newHighscore();
        }
    }

    public void clickRetry(View view) {
        Intent intent = new Intent(this, StartQuizActivity.class);
        startActivity(intent);
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
        TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[]{com.google.android.material.R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }
}
