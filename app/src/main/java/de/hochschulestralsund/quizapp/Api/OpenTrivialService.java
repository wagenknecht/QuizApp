package de.hochschulestralsund.quizapp.Api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hochschulestralsund.quizapp.Entities.Question;

public class OpenTrivialService {
    private List<Question> questions;
    public List<Question> getQuestions()  {
        questions=new ArrayList<>();
        OpenTrivialRestClient.get(10, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    TypeReference<List<Question>> mapType = new TypeReference<List<Question>>() {};
                    questions = mapper.readValue(response.get("results").toString(), mapType);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return questions;
    }
}
