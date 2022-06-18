package de.hochschulestralsund.quizapp.Api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class OpenTrivialRestClient {
    private static final String BASE_URL= "https://opentdb.com/api.php";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(int amount, RequestParams params, AsyncHttpResponseHandler responseHandler){
        // TODO: Params does not seem to be needed. Investigate later.
        client.get(getAbsoluteUrl(amount), params, responseHandler);

    }

    private static String getAbsoluteUrl(int amount){
        return BASE_URL+ "?amount="+ String.valueOf(amount)+"&type=multiple";
    }

}
