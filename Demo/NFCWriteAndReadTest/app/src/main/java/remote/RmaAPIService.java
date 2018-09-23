package remote;

import model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by elpsychris on 03/12/2018.
 */

public interface RmaAPIService {
    @GET("/test")
    Call<String> getServer();

    @GET("/user/{id}")
    Call<User> getUserById(@Path("id") Integer userId);
}
