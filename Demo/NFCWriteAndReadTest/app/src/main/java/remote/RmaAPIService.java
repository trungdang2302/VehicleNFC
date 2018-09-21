package remote;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by elpsychris on 03/12/2018.
 */

public interface RmaAPIService {
    @GET("/test")
    Call<String> getServer();
}
