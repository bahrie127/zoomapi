package bots;

import clients.OAuthClient;
import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.ini4j.Wini;
import xyz.dmanchon.ngrok.client.NgrokTunnel;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

public class OAuthBot {

    private static final String SECTION_NAME = "OAuth";

    public static void main(String[] args) throws IOException, UnirestException, OAuthProblemException, OAuthSystemException, URISyntaxException, InterruptedException {
        Wini ini = new Wini(new File(OAuthBot.class.getClassLoader().getResource("bot.ini").getFile()));

        String clientId = ini.get(SECTION_NAME, "client_id", String.class);
        String clientSecret = ini.get(SECTION_NAME, "client_secret", String.class);
        Integer port = ini.get(SECTION_NAME, "port", Integer.class);

        NgrokTunnel tunnel = new NgrokTunnel(4041);
        String redirectUri = tunnel.url();

        OAuthClient client = new OAuthClient(clientId, clientSecret, port, redirectUri, 5);

        Gson gson = new Gson();

        HttpResponse userResponse = client.getUserV2().get("me", null);
        String user = gson.toJson(userResponse.body());
        System.out.println(user);
        System.out.println("----------");

        tunnel.close();
    }
}
