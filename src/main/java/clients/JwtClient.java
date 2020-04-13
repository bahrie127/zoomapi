package clients;

import com.mashape.unirest.http.exceptions.UnirestException;

public class JwtClient extends ZoomClient {

    public JwtClient(String apiKey, String apiSecret, String dataType, Integer timeout) throws UnirestException {
        super(apiKey, apiSecret, dataType, timeout);
    }

    public void refreshToken() { }
}
