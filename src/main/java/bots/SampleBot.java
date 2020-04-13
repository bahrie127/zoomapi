package bots;

import api.ApiClient;
import api.TokenHandler;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

public class SampleBot {

    public static void main(String[] args) throws OAuthProblemException, OAuthSystemException, IOException, InterruptedException, UnirestException, URISyntaxException {
        ApiClient apiClient = new ApiClient("https://api.zoom.us/v2", 15);
        HttpResponse<String> response = apiClient.getRequest("/users/me", null);

        System.out.println(response.body());
    }
}
