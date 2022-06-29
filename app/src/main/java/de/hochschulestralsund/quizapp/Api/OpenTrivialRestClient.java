package de.hochschulestralsund.quizapp.Api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.Entities.Difficulty;

public class OpenTrivialRestClient {
    private static final String BASE_URL = "https://opentdb.com/api.php";

    private static AsyncHttpClient client = new AsyncHttpClient();

    // Get Method on Api with generated URL
    public static void get(int amount, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(amount), params, responseHandler);
    }

    // Get Method on Api with generated URL
    public static void get(int amount, Difficulty difficulty, Category category, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        System.out.println(getAbsoluteUrl(amount, difficulty, category));
        client.get(getAbsoluteUrl(amount, difficulty, category), requestParams, responseHandler);
    }

    //  Generating URL with amount of questions requested
    private static String getAbsoluteUrl(int amount) {
        return BASE_URL + "?amount=" + String.valueOf(amount) + "&type=multiple";
    }

    // Generating URL with amount of questions, difficulty and category
    // If difficulty is null do not set
    private static String getAbsoluteUrl(int amount, Difficulty difficulty, Category category) {
        String url = getAbsoluteUrl(amount) + "&category=" + category.getId();
        if (difficulty == null) return url;
        return url + "&difficulty=" + difficulty.getValue();
    }

}
