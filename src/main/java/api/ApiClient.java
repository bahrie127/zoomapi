package api;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.util.Map;

public class ApiClient {

    public static HttpResponse getRequest(String endpoint, Map<String, ?> params) throws IOException {
        GenericUrl url = new GenericUrl(endpoint);
        url.putAll(params);

        HttpTransport transport = new NetHttpTransport();
        Credential credential =
                new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken("accessToken");
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);

        return requestFactory.buildGetRequest(url).execute();

    }
}
