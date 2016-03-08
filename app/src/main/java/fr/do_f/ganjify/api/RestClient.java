package fr.do_f.ganjify.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by do_f on 22/02/16.
 */
public class RestClient
{

    private static String token;
    private static ApiService restClient;
    private static ApiService restClientToken;

    private static String WEBSERVICE_HOST = " http://romain-zanchi.com:3420/api/";

    static {
        setupRestClient();
    }

    private RestClient()
    {

    }

    public static ApiService get()
    {
        return restClient;
    }

    public static ApiService get(String _token)
    {
        if (token == null)
            setupRestClientToken(_token);
        return restClientToken;
    }


    private static void setupRestClient()
    {
        Log.d("RestClient", "setupRestClient");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEBSERVICE_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restClient = retrofit.create(ApiService.class);
    }

    private static void setupRestClientToken(final String _token)
    {
        Log.d("RestClient", "setupRestClientToken");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Customize the request
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer "+_token)
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);

                // Customize or return the response
                return response;
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEBSERVICE_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        restClientToken = retrofit.create(ApiService.class);
    }
}
