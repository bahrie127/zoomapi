package clients;

import api.TokenHandler;
import com.mashape.unirest.http.exceptions.UnirestException;
import components.UserV2;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;

public class OAuthClient extends ZoomClient {

    private Integer port;
    private String redirectUri;

    public OAuthClient(String clientId, String clientSecret, Integer port,
                       String redirectUri, String dataType, Integer timeout) throws UnirestException,
            OAuthSystemException, OAuthProblemException, IOException {

        super(clientId, clientSecret, dataType, timeout);

        this.port = port;
        this.redirectUri = redirectUri;
        setToken(new TokenHandler().getOAuthToken(clientId, clientSecret, redirectUri));
    }

    /**
     * Refreshes OAuth access token
     *
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     * @throws IOException
     * @throws UnirestException
     */
    public void refreshToken() throws OAuthProblemException, OAuthSystemException, IOException, UnirestException {
        setToken(new TokenHandler().getOAuthToken(this.getApiKey(), this.getApiSecret(), redirectUri));
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
