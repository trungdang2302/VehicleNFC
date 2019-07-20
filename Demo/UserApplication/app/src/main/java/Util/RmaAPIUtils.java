package Util;

import remote.RetrofitClient;
import remote.RmaAPIService;

/**
 * Created by Swomfire on 19-Sep-18.
 */

public class RmaAPIUtils {
    public static final String LOCAL_IP = "http://192.168.137.1";
    public static final String PORT = "8080";
    public static final String BASE_URL = LOCAL_IP + ":" + PORT;

    public static RmaAPIService getAPIService() {
        RmaAPIService rmaAPIService = RetrofitClient.getClient(BASE_URL).create(RmaAPIService.class);
        return rmaAPIService;
    }
}
