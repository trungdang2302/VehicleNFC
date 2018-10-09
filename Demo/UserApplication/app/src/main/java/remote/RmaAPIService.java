package remote;

import model.Order;
import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by elpsychris on 03/12/2018.
 */

public interface RmaAPIService {
    @GET("/test")
    Call<String> getServer();

    @GET("/user/{id}")
    Call<User> getUserById(@Path("id") Integer userId);

//
//    @GET("/meter/{id}")
//    Call<Meter> getMeterById(@Path("id") Integer meterId);

    @POST("/transaction/create")
    @Headers({"Content-Type: application/json"})
    Call<Order> sendTransactionToServer(@Body Order transaction);

    @POST("/user/login")
    @FormUrlEncoded
    Call<User> login(@Field("phone") String phone, @Field("password") String password);
}
