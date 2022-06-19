package de.hochschulestralsund.quizapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

import de.hochschulestralsund.quizapp.Api.OpenTrivialService;
import de.hochschulestralsund.quizapp.Api.QuestionResponseCallback;
import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.Entities.Difficulty;
import de.hochschulestralsund.quizapp.Entities.Question;
import de.hochschulestralsund.quizapp.R;

public class startQuizActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public List<Question> QuestionList;

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

    public void startQuiz(View view){
        Intent intent = new Intent(this, QuizActivity.class);
        OpenTrivialService openTrivialService =new OpenTrivialService();
        //todo add right selector
        openTrivialService.getQuestions(10, Difficulty.MEDIUM, Category.ANIMALS, new QuestionResponseCallback() {
            @Override
            public void onQuestionResponse(List<Question> questionList) {
                questionList.forEach(question -> intent.putExtra("question", (Serializable) questionList));
                startActivity(intent);
            }
        });
    }
}