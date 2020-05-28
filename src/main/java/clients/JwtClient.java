package clients;

import exceptions.InvalidEntityException;

public class JwtClient extends ZoomClient {

    public JwtClient(String apiKey, String apiSecret, Integer timeout) throws InterruptedException, InvalidEntityException {
        super(apiKey, apiSecret, timeout);

        //TODO: get JWT token
    }

    @Override
    public void refreshToken() {
        //TODO: get JWT token
    }
}
