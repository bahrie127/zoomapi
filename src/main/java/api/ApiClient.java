package api;

import com.google.gson.Gson;
import exceptions.InvalidRequestException;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class ApiClient {

    //using a rate lower than the documentation as advised, 1 call every 2 seconds since it is a basic plan
    private static Throttle throttle = new Throttle(1, 2000L);
    private String baseUri;
    private Integer timeout;
    private String token = null;
    private Gson gson;

    private static ApiClient instance = null;

    private ApiClient() {
        this.gson = new Gson();
    }

    public static ApiClient getThrottledInstance() throws InterruptedException {
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
     * @return HttpResponse
     * @throws InvalidRequestException
     */
    public HttpResponse getRequest(String endpoint) throws InvalidRequestException {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlFor(endpoint)))
                    .timeout(Duration.ofSeconds(this.timeout))
                    .setHeader("Authorization", "Bearer " + this.token)
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            throw new InvalidRequestException(exception.getMessage());
        }
    }

    /**
     * HTTP GET method request
     *
     * @param endpoint Path URL
     * @param params Query parameters
     * @return HttpResponse
     * @throws InvalidRequestException
     */
    public HttpResponse getRequest(String endpoint, Map<String, Object> params) throws InvalidRequestException {
        try {
            HttpClient client = HttpClient.newHttpClient();

            URIBuilder uriBuilder = new URIBuilder(urlFor(endpoint));
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uriBuilder.toString()))
                    .timeout(Duration.ofSeconds(this.timeout))
                    .setHeader("Authorization", "Bearer " + this.token)
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | URISyntaxException exception) {
            throw new InvalidRequestException(exception.getMessage());
        }
    }

    /**
     * HTTP POST method request
     *
     * @param endpoint Path URL
     * @param params Query parameters
     * @param data Request body
     * @return HttpResponse
     * @throws InvalidRequestException
     */
    public HttpResponse postRequest(String endpoint, Map<String, Object> params, Map<String, Object> data) throws InvalidRequestException {
        try {
            HttpClient client = HttpClient.newHttpClient();

            URIBuilder uriBuilder = new URIBuilder(urlFor(endpoint));
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uriBuilder.toString()))
                    .timeout(Duration.ofSeconds(this.timeout))
                    .setHeader("Authorization", "Bearer " + this.token)
                    .setHeader("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | URISyntaxException exception) {
            throw new InvalidRequestException(exception.getMessage());
        }
    }

    /**
     * HTTP POST method request
     *
     * @param endpoint Path URL
     * @return HttpResponse
     * @throws InvalidRequestException
     */
    public HttpResponse postRequest(String endpoint, Map<String, Object> data) throws InvalidRequestException {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlFor(endpoint)))
                    .timeout(Duration.ofSeconds(this.timeout))
                    .setHeader("Authorization", "Bearer " + this.token)
                    .setHeader("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            throw new InvalidRequestException(exception.getMessage());
        }
    }

    /**
     * HTTP PUT method request
     *
     * @param endpoint Path URL
     * @param data Request body
     * @return HttpResponse
     * @throws InvalidRequestException
     */
    public HttpResponse putRequest(String endpoint, Map<String, Object> data) throws InvalidRequestException {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlFor(endpoint)))
                    .timeout(Duration.ofSeconds(this.timeout))
                    .setHeader("Authorization", "Bearer " + this.token)
                    .setHeader("Content-type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            throw new InvalidRequestException(exception.getMessage());
        }
    }

    /**
     * HTTP DELETE method request
     *
     * @param endpoint Path URL
     * @return HttpResponse
     * @throws InvalidRequestException
     */
    public HttpResponse deleteRequest(String endpoint) throws InvalidRequestException {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlFor(endpoint)))
                    .timeout(Duration.ofSeconds(this.timeout))
                    .setHeader("Authorization", "Bearer " + this.token)
                    .setHeader("Content-type", "application/json")
                    .DELETE()
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            throw new InvalidRequestException(exception.getMessage());
        }
    }

    /**
     * HTTP DELETE method request
     *
     * @param endpoint Path URL
     * @param params Query parameters
     * @return HttpResponse
     * @throws InvalidRequestException
     */
    public HttpResponse deleteRequest(String endpoint, Map<String, Object> params) throws InvalidRequestException {
        try {
            HttpClient client = HttpClient.newHttpClient();

            URIBuilder uriBuilder = new URIBuilder(urlFor(endpoint));
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uriBuilder.toString()))
                    .timeout(Duration.ofSeconds(this.timeout))
                    .setHeader("Authorization", "Bearer " + this.token)
                    .setHeader("Content-type", "application/json")
                    .DELETE()
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | URISyntaxException exception) {
            throw new InvalidRequestException(exception.getMessage());
        }
    }

    /**
     * HTTP PATCH method request
     *
     * @param endpoint Path URL
     * @param data Request body
     * @return HttpResponse
     * @throws InvalidRequestException
     */
    public HttpResponse patchRequest(String endpoint, Map<String, Object> data) throws InvalidRequestException {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlFor(endpoint)))
                    .timeout(Duration.ofSeconds(this.timeout))
                    .setHeader("Authorization", "Bearer " + this.token)
                    .setHeader("Content-type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            throw new InvalidRequestException(exception.getMessage());
        }
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
