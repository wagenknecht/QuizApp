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

import java.util.ArrayList;
import java.util.List;

import de.hochschulestralsund.quizapp.Adapter.ScoreAdapter;
import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.R;

public class ViewScoresActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private int score;
    private String category;

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
        mAdapter = new ScoreAdapter(easy);
        recyclerView.setAdapter(mAdapter);
        Spinner selectCategorySpinner = findViewById(R.id.spinnerCategory);
        selectCategorySpinner.setAdapter(new ArrayAdapter<Category>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Category.values()));
        if (getIntent().getExtras() != null) {
            score = (Integer) getIntent().getSerializableExtra("score");
            category = (String) getIntent().getSerializableExtra("category");
            //        if (score>=DatabaseHighsore)
            newHighscore();
        }
    }

    public void zurueck(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void easy(View view) {
        List<String> easy = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            easy.add("easy" + i);
        }// define an adapter
        mAdapter = new ScoreAdapter(easy);
        recyclerView.setAdapter(mAdapter);
    }

    public void medium(View view) {
        List<String> medium = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            medium.add("medium" + i);
        }
        mAdapter = new ScoreAdapter(medium);
        recyclerView.setAdapter(mAdapter);
    }

    public void hard(View view) {
        List<String> hard = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            hard.add("hard" + i);
        }
        mAdapter = new ScoreAdapter(hard);
        recyclerView.setAdapter(mAdapter);
    }

    public void newHighscore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                ViewScoresActivity.this
        );
        builder.setTitle("\uD83C\uDF89 new high score: " + score + " points in " + category + "\uD83C\uDF89");
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
