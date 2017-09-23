package pvtitov.myclients;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pvtitov.myclients.api.Info;
import pvtitov.myclients.api.RandomUserApi;
import pvtitov.myclients.api.RandomUserModel;
import pvtitov.myclients.api.Result;
import pvtitov.myclients.model.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Павел on 23.09.2017.
 */

public class MyApplication extends Application {
    private static RandomUserApi randomUserApi;

    public static RandomUserApi getRandomUserApi() {
        return randomUserApi;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        randomUserApi = retrofit.create(RandomUserApi.class);
    }
}
