package clients;

import api.TokenHandler;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;

public class OAuthClient extends ZoomClient {

    private Integer port;
    private String redirectUri;
    private String browserPath;
    private String token;

    public OAuthClient(String clientId, String clientSecret, Integer port,
                       String redirectUri, String browserPath, String dataType, Integer timeout) throws UnirestException,
            OAuthSystemException, OAuthProblemException, IOException {

        super(clientId, clientSecret, dataType, timeout);

        this.port = port;
        this.redirectUri = redirectUri;
        this.browserPath = browserPath;
        this.token = new TokenHandler().getOAuthToken(clientId, clientSecret, redirectUri);
    }

    public void refreshToken() throws OAuthProblemException, OAuthSystemException, IOException, UnirestException {
        this.token = new TokenHandler().getOAuthToken(this.getApiKey(), this.getApiSecret(), redirectUri);
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

    public String getBrowserPath() {
        return browserPath;
    }

    public void setBrowserPath(String browserPath) {
        this.browserPath = browserPath;
    }
}
