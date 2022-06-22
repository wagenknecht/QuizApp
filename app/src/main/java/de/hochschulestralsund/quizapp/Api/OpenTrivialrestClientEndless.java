package de.hochschulestralsund.quizapp.Api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import de.hochschulestralsund.quizapp.Entities.Category;

public class OpenTrivialrestClientEndless {
    private static final String BASE_URL = "https://opentdb.com/api.php";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(int amount, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        // TODO: Params does not seem to be needed. Investigate later.
        client.get(getAbsoluteUrl(amount), params, responseHandler);
    }

    public static void get(int amount, Category category, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        System.out.println(getAbsoluteUrl(amount, category));
        client.get(getAbsoluteUrl(amount, category), requestParams, responseHandler);
    }

    private static String getAbsoluteUrl(int amount) {
        return BASE_URL + "?amount=" + String.valueOf(amount) + "&type=multiple";
    }

    private static String getAbsoluteUrl(int amount, Category category) {
        return getAbsoluteUrl(amount) + "&category=" + category.getValue();
    }

}
