package de.hochschulestralsund.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class startQuizActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        //Dummy Adapter für Spinner Kategorie bis API/DB
        Spinner selectCategorySpinner = findViewById(R.id.selectCategorySpinner);
        ArrayAdapter<CharSequence> selectCategorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        selectCategorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategorySpinner.setAdapter(selectCategorySpinnerAdapter);
        selectCategorySpinner.setOnItemSelectedListener(this);

        //Dummy Adapter für Spinner Schwierigkeit bis API/DB
        Spinner selectDifficultySpinner = findViewById(R.id.selectDifficultySpinner);
        ArrayAdapter<CharSequence> selectDifficultyAdapter = ArrayAdapter.createFromResource(this,
                R.array.difficulties, android.R.layout.simple_spinner_item);
        selectDifficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectDifficultySpinner.setAdapter(selectDifficultyAdapter);
        selectDifficultySpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}