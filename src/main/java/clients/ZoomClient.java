package clients;

import api.ApiClient;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ZoomClient extends ApiClient {

    private static final String API_BASE_URIS = "https://api.zoom.us/v2";
    private String apiKey;
    private String apiSecret;
    private String dataType;

    public ZoomClient(String apiKey, String apiSecret, String dataType, Integer timeout) throws UnirestException {
        super(this.API_BASE_URIS, timeout);

        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.dataType = dataType;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public String getDataType() {
        return dataType;
    }
}
