package com.example.chucknorrisapp;


import com.example.chucknorrisapp.pogo.Joke;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class NetworkService {

    private static NetworkService mInstance;
    private static final String BASE_URL = "https://api.icndb.com";
    private final Retrofit mRetrofit;

    //Singleton for class
    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }
// declare and initialize Retrofit
    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    public interface APIService {
        @GET("/jokes/random/{count}")
        Call<Joke> getRandomJokesWithCount(@Path("count") int count);
    }

    public APIService getAPI() {
        return mRetrofit.create(APIService.class);
    }
}
