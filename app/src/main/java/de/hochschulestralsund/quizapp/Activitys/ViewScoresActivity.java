package de.hochschulestralsund.quizapp.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hochschulestralsund.quizapp.Adapter.ScoreAdapter;
import de.hochschulestralsund.quizapp.Database.AppDatabase;
import de.hochschulestralsund.quizapp.Database.Bestenliste;
import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.R;

public class ViewScoresActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase database;
    private Spinner selectCategorySpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_highscore_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //test data
        List<String> easy = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            easy.add("easy" + i);
        }
        //mAdapter = new ScoreAdapter(easy);
        recyclerView.setAdapter(mAdapter);
        Spinner selectCategorySpinner = findViewById(R.id.spinnerCategory);
        selectCategorySpinner.setAdapter(new ArrayAdapter<Category>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Category.values()));
    }

    public void zurueck(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void easy(View view){
        /*List<String> easy = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            easy.add("easy" + i);
        }*/
        // define an adapter
        //get Table Bestenliste
        List<Bestenliste> bestenliste = database.bestenlisteDao().getBestenlisteCategoryEntry(selectCategorySpinner.toString());
        mAdapter = new ScoreAdapter(bestenliste);
        recyclerView.setAdapter(mAdapter);
    }

    public void medium(View view){
        List<String> medium = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            medium.add("medium" + i);
        }
        //mAdapter = new ScoreAdapter(medium);
        recyclerView.setAdapter(mAdapter);
    }

    public void hard(View view){
        List<String> hard = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            hard.add("hard" + i);
        }
        //mAdapter = new ScoreAdapter(hard);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
