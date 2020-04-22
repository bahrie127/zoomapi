package clients;

import api.TokenHandler;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;

public class OAuthClient extends ZoomClient {

    private Integer port;
    private String redirectUri;
    private TokenHandler tokenHandler;

    public OAuthClient(String clientId, String clientSecret, Integer port,
                       String redirectUri, Integer timeout) throws OAuthSystemException,
            OAuthProblemException, IOException, InterruptedException {

        super(clientId, clientSecret, timeout);

        this.port = port;
        this.redirectUri = redirectUri;
        this.tokenHandler = new TokenHandler();

        setToken(tokenHandler.getOAuthToken(clientId, clientSecret, redirectUri));
    }

    /**
     * Refreshes OAuth access token
     *
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     * @throws IOException
     * @throws UnirestException
     */
    @Override
    public void refreshToken() {
        try {
            setToken(tokenHandler.getOAuthToken(this.getApiKey(), this.getApiSecret(), redirectUri));
        } catch (Exception exception) {
            System.out.println("Error getting authorization token.");
        }
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
