package api;

import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class ApiClient {

    private static Throttle throttle = new Throttle();
    private String baseUri;
    private Integer timeout;
    private String token = null;

    private static ApiClient instance = null;

    private ApiClient() { }

    public static ApiClient getInstance() throws InterruptedException {
        throttle.permit();
        if (instance == null) {
            instance = new ApiClient();
        }

        return instance;
    }

    /**
     * Generates API path URL
     *
     * @param endpoint Path URL
     * @return Concatenated baseUri and endpoint
     */
    protected String urlFor(String endpoint) {
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
            .timeout(Duration.ofSeconds(this.timeout))
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
                .timeout(Duration.ofSeconds(this.timeout))
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
                .timeout(Duration.ofSeconds(this.timeout))
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
                .timeout(Duration.ofSeconds(this.timeout))
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
                .timeout(Duration.ofSeconds(this.timeout))
                .setHeader("Authorization", "Bearer " + this.token)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
