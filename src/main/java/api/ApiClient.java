package api;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import xyz.dmanchon.ngrok.client.NgrokTunnel;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
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

    public HttpResponse getRequest(String endpoint, List<NameValuePair> params) throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();

        URIBuilder uriBuilder = new URIBuilder(urlFor(endpoint));
        if (params != null) {
            uriBuilder.addParameters(params);
        }

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uriBuilder.toString()))
            .setHeader("Authorization", "Bearer " + this.token)
            .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse postRequest(String endpoint, List<NameValuePair> params, Map<String, Object> data) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        URIBuilder uriBuilder = new URIBuilder(urlFor(endpoint));
        if (params != null) {
            uriBuilder.addParameters(params);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBuilder.toString()))
                .setHeader("Authorization", "Bearer " + this.token)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse putRequest(String endpoint, List<NameValuePair> params, Map<String, Object> data) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        URIBuilder uriBuilder = new URIBuilder(urlFor(endpoint));
        if (params != null) {
            uriBuilder.addParameters(params);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBuilder.toString()))
                .setHeader("Authorization", "Bearer " + this.token)
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse deleteRequest(String endpoint, List<NameValuePair> params) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        URIBuilder uriBuilder = new URIBuilder(urlFor(endpoint));
        if (params != null) {
            uriBuilder.addParameters(params);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBuilder.toString()))
                .setHeader("Authorization", "Bearer " + this.token)
                .DELETE()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse patchRequest(String endpoint, List<NameValuePair> params,  Map<String, Object> data) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        URIBuilder uriBuilder = new URIBuilder(urlFor(endpoint));
        if (params != null) {
            uriBuilder.addParameters(params);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBuilder.toString()))
                .setHeader("Authorization", "Bearer " + this.token)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String getBaseUri() {
        return baseUri;
    }

    public Integer getTimeout() {
        return timeout;
    }
}
