package inburst.peoplemon.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import inburst.peoplemon.PeopleMon;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lennyhicks on 10/31/16.
 */
public class NewRestClient {
    private ApiService apiService;

    public NewRestClient(){
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        //builder.setDateFormat("yyyy-MM-dd’T'HH:mm:ss.SSSSSSSZ");
        Gson gson = builder.create();

        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new SessionRequestInterceptor())
                .addInterceptor(log)
                .build();

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(PeopleMon.API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = restAdapter.create(ApiService.class);


    }

    public ApiService getApiService() {
        return apiService;
    }
}
