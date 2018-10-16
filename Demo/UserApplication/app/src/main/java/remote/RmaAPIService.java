package remote;

import java.util.List;

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
import retrofit2.http.Query;

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

    @GET("/order/get-order/{id}")
    Call<Order> getOrderById(@Path("id") Integer orderId);

    @GET("/order/open-order/{userId}")
    Call<Order> getOpenOrderByUserId(@Path("userId") Integer userId);


    @POST("/user/top-up")
    @FormUrlEncoded
    Call<User> topUp(@Field("userId") String userId, @Field("amount") double amount);

    @GET("/order/orders")
    Call<List<Order>> getOrderByUserId(@Query("userId") Integer userId);

    @POST("/user/create-user")
    @Headers({"Content-Type: application/json"})
    Call<User> sendUserToServer(@Body User user);

}
