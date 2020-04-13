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

    /**
     * Generates API path URL
     *
     * @param endpoint Path URL
     * @return Concatenated baseUri and endpoint
     */
    private String urlFor(String endpoint) {
        return this.baseUri + endpoint;
    }

    /**
     * HTTP GET method request
     *
     * @param endpoint Path URL
     * @param params Query parameters
     * @return HttpResponse
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
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

    /**
     * HTTP POST method request
     *
     * @param endpoint Path URL
     * @param params Query parameters
     * @param data Request body
     * @return HttpResponse
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
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

    /**
     * HTTP PUT method request
     *
     * @param endpoint Path URL
     * @param params Query parameters
     * @param data Request body
     * @return HttpRequest
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
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

    /**
     * HTTP DELETE method request
     *
     * @param endpoint Path URL
     * @param params Query parameters
     * @return HttpRequest
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
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

    /**
     * HTTP PATCH method request
     *
     * @param endpoint Path URL
     * @param params Query parameters
     * @param data Request body
     * @return HttpRequest
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
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
