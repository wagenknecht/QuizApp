package de.hochschulestralsund.quizapp.Api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.Entities.Difficulty;
import de.hochschulestralsund.quizapp.Entities.Question;

public class OpenTrivialService {


    // Method for test purposes
    public void getQuestions(QuestionResponseCallback callback) {
        OpenTrivialRestClient.get(10, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    TypeReference<List<Question>> mapType = new TypeReference<List<Question>>() {};
                    callback.onQuestionResponse(mapper.readValue(response.get("results").toString(), mapType));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void getQuestions(int amount, Difficulty difficulty, Category category, QuestionResponseCallback callback) {
        OpenTrivialRestClient.get(amount, difficulty, category, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    TypeReference<List<Question>> mapType = new TypeReference<List<Question>>() {};
                    callback.onQuestionResponse(mapper.readValue(response.get("results").toString(), mapType));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
