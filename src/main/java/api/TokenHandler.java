package api;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

public class TokenHandler {

    private String code;

    /**
     * Opens a HTTP receiver to obtain the authorization code required for the token
     * also sends back a blank content response to client browser
     *
     * @param port Port number to listen to
     * @throws IOException
     */
    private void httpReceiver(Integer port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = bufferedReader.readLine();
        String[] requestMethodAttributes = data.split(" ");
        String code = requestMethodAttributes[1].split("=")[1];
        this.code = code;

        String httpResponse = "HTTP/1.1 200 OK\r\n\r\n";
        socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
        socket.getOutputStream().flush();

        serverSocket.close();
    }

    private void openBrowser(String redirectUri) throws IOException {
        URI myUri = URI.create(redirectUri);
        Desktop.getDesktop().browse(myUri);
    }

    /**
     * Obtains OAuth access token
     *
     * @param clientId Client ID
     * @param clientSecret Client Secret
     * @param redirectUri Redirection URI to be used for obtaining authorization code
     * @return Access Token for authentication purposes
     * @throws OAuthSystemException
     * @throws OAuthProblemException
     * @throws UnirestException
     * @throws IOException
     */
    public String getOAuthToken(String clientId, String clientSecret,
                                String redirectUri) throws OAuthSystemException, OAuthProblemException, UnirestException, IOException {

        OAuthClientRequest authorizationCodeRequest = OAuthClientRequest
                .authorizationLocation("https://zoom.us/oauth/authorize")
                .setResponseType("code")
                .setClientId(clientId)
                .setRedirectURI(redirectUri)
                .buildQueryMessage();

        System.out.println("Opening browser for authentication at " + authorizationCodeRequest.getLocationUri());
        openBrowser(authorizationCodeRequest.getLocationUri());
        this.httpReceiver(4041);

        OAuthClientRequest accessTokenRequest = OAuthClientRequest
                .tokenLocation("https://zoom.us/oauth/token")
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setRedirectURI(redirectUri)
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setParameter("response_type", ResponseType.CODE.toString())
                .setCode(this.code)
                .buildQueryMessage();

        OAuthClient client = new OAuthClient(new URLConnectionClient());

        return client.accessToken(accessTokenRequest, OAuth.HttpMethod.POST, OAuthJSONAccessTokenResponse.class)
                .getAccessToken();
    }
}
