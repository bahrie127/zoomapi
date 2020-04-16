package clients;

import api.ApiClient;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;

public class ZoomClient extends ApiClient {

    private String apiKey;
    private String apiSecret;
    private String dataType;

    public ZoomClient(String apiKey, String apiSecret, String dataType, Integer timeout) throws UnirestException {
        super("https://api.zoom.us/v2", timeout);

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

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }
}
