package de.hochschulestralsund.quizapp.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import de.hochschulestralsund.quizapp.Api.OpenTrivialService;
import de.hochschulestralsund.quizapp.Api.QuestionResponseCallback;
import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.Entities.Difficulty;
import de.hochschulestralsund.quizapp.Entities.Question;
import de.hochschulestralsund.quizapp.R;

public class StartEndlessActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_endless);

        btnStart = findViewById(R.id.startQuiz);
        //fil the Spinner with the Category's
        Spinner selectCategorySpinner = findViewById(R.id.selectCategorySpinner);
        ArrayAdapter categoryAdapter = new ArrayAdapter<>(this, R.layout.spinner_selected_item, Category.values());
        categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        selectCategorySpinner.setAdapter(categoryAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnStart.setClickable(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void startQuiz(View view) {
        btnStart.setClickable(false);
        Intent intent = new Intent(this, EndlessQuizActivity.class);
        OpenTrivialService openTrivialService = new OpenTrivialService();
        Spinner cat = findViewById(R.id.selectCategorySpinner);
        //add the Category to the intent
        intent.putExtra("category", (Serializable) cat.getSelectedItem());
        //add the difficulty to the intent
        openTrivialService.getQuestions(10, (Category) cat.getSelectedItem(), new QuestionResponseCallback() {
            @Override
            public void onQuestionResponse(List<Question> questionList) {
                //add the Questionlist to the intent
                questionList.forEach(question -> intent.putExtra("question", (Serializable) questionList));
                startActivity(intent);
            }
        });
    }
}