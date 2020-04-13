package bots;

import clients.OAuthClient;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.ini4j.Wini;
import xyz.dmanchon.ngrok.client.NgrokTunnel;

import java.io.File;
import java.io.IOException;

public class OAuthBot {

    private static final String SECTION_NAME = "OAuth";

    public static void main(String[] args) throws IOException, UnirestException, OAuthProblemException, OAuthSystemException {
        Wini ini = new Wini(new File(OAuthBot.class.getClassLoader().getResource("bot.ini").getFile()));

        String clientId = ini.get(SECTION_NAME, "client_id", String.class);
        String clientSecret = ini.get(SECTION_NAME, "client_secret", String.class);
        Integer port = ini.get(SECTION_NAME, "port", Integer.class);
        String browserPath = ini.get(SECTION_NAME, "browser_path", String.class);

        NgrokTunnel tunnel = new NgrokTunnel(4041);
        String redirectUri = tunnel.url();

        OAuthClient client = new OAuthClient(clientId, clientSecret, port, redirectUri, browserPath, "json", 15);
    }
}
