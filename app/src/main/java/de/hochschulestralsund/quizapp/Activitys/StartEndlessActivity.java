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

import de.hochschulestralsund.quizapp.Api.OpenTrivialServiceEndless;
import de.hochschulestralsund.quizapp.Api.QuestionResponseCallback;
import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.Entities.Question;
import de.hochschulestralsund.quizapp.R;

public class StartEndlessActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_endless);

        btnStart = findViewById(R.id.startQuiz);

        Spinner selectCategorySpinner = findViewById(R.id.selectCategorySpinner);
        selectCategorySpinner.setAdapter(new ArrayAdapter<Category>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Category.values()));
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
        OpenTrivialServiceEndless openTrivialService = new OpenTrivialServiceEndless();
        Spinner cat = findViewById(R.id.selectCategorySpinner);
        intent.putExtra("category", (Category) cat.getSelectedItem());
        openTrivialService.getQuestions(10, (Category) cat.getSelectedItem(), new QuestionResponseCallback() {
            @Override
            public void onQuestionResponse(List<Question> questionList) {
                questionList.forEach(question -> intent.putExtra("question", (Serializable) questionList));
                startActivity(intent);
            }
        });
    }
}