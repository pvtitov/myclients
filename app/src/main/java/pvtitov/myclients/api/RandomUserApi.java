package pvtitov.myclients.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Павел on 22.09.2017.
 */

public interface RandomUserApi {
    @GET("/api/")
    //Call<RandomUserModel> getUsers (@Query("results") int results);
    Call<RandomUserModel> getUser();
}
