package api;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

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
    private String token = null;

    public ApiClient(String baseUri, Integer timeout) throws UnirestException {
        this.baseUri = baseUri;
        this.timeout = timeout;
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

    public void setToken(String token) {
        this.token = token;
    }
}
