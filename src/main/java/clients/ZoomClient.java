package clients;

import api.ApiClient;
import com.mashape.unirest.http.exceptions.UnirestException;
import components.*;

public class ZoomClient extends ApiClient {

    private String apiKey;
    private String apiSecret;
    private String dataType;
    private UserV2 userV2;

    public ZoomClient(String apiKey, String apiSecret, String dataType, Integer timeout, UserV2 userV2) throws UnirestException {
        super("https://api.zoom.us/v2", timeout);

        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.dataType = dataType;
        this.userV2 = userV2;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }

    public UserV2 getUserV2() {
        return UserV2.getInstance();
    }
}
