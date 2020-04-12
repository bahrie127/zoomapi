package api;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import xyz.dmanchon.ngrok.client.NgrokTunnel;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ApiClient {

    private String baseUri;
    private Integer timeout;
    private String token;

    public ApiClient(String baseUri, Integer timeout) throws UnirestException {
        this.baseUri = baseUri;
        this.timeout = timeout;

        //Remove after
        NgrokTunnel tunnel = new NgrokTunnel(4041);
        String redirectUri = tunnel.url();
        try {
            this.token = new TokenHandler().getOAuthToken("CLIENT_ID",
                    "CLIENT_SECRET", redirectUri);
            System.out.println("Token: " + this.token);
        } catch (OAuthSystemException e) {
            System.out.println(e.getMessage());
        } catch (OAuthProblemException e) {
            System.out.println(e.getMessage());
        } catch (UnirestException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String urlFor(String endpoint) {
        return this.baseUri + endpoint;
    }

    public HttpResponse getRequest(String endpoint, Map<String, ?> params) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(urlFor(endpoint)))
            .setHeader("Authorization", "Bearer " + this.token)
            .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
