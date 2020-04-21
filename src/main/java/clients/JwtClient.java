package clients;

public class JwtClient extends ZoomClient {

    public JwtClient(String apiKey, String apiSecret, Integer timeout) throws InterruptedException {
        super(apiKey, apiSecret, timeout);

        //TODO: get JWT token
    }

    @Override
    public void refreshToken() {
        //TODO: get JWT token
    }
}
