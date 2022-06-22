package de.hochschulestralsund.quizapp.Api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import de.hochschulestralsund.quizapp.Entities.Category;
import de.hochschulestralsund.quizapp.Entities.Difficulty;

public class OpenTrivialRestClient {
    private static final String BASE_URL= "https://opentdb.com/api.php";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(int amount, RequestParams params, AsyncHttpResponseHandler responseHandler){
        // TODO: Params does not seem to be needed. Investigate later.
        client.get(getAbsoluteUrl(amount), params, responseHandler);

    }

    public static void get(int amount, Difficulty difficulty, Category category, RequestParams requestParams, AsyncHttpResponseHandler responseHandler){
        System.out.println(getAbsoluteUrl(amount, difficulty, category));
        client.get(getAbsoluteUrl(amount, difficulty, category), requestParams, responseHandler);
    }

    private static String getAbsoluteUrl(int amount){
        return BASE_URL+ "?amount="+ String.valueOf(amount)+"&type=multiple";
    }

    private static String getAbsoluteUrl(int amount, Difficulty difficulty, Category category){
        return getAbsoluteUrl(amount)+ "&category="+ category.getValue() +"&difficulty="+ difficulty.getValue();
    }

}
