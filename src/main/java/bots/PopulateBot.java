package bots;

import clients.OAuthClient;
import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import exceptions.InvalidComponentException;
import models.Channel;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.ini4j.Wini;
import xyz.dmanchon.ngrok.client.NgrokTunnel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PopulateBot {

    private static final String SECTION_NAME = "OAuth";

    public static void main(String[] args) throws UnirestException, IOException, OAuthSystemException, InterruptedException, OAuthProblemException, InvalidComponentException {

        Wini ini = new Wini(new File(OAuthBot.class.getClassLoader().getResource("bot.ini").getFile()));

        String clientId = ini.get(SECTION_NAME, "client_id", String.class);
        String clientSecret = ini.get(SECTION_NAME, "client_secret", String.class);
        Integer port = ini.get(SECTION_NAME, "port", Integer.class);

        NgrokTunnel tunnel = new NgrokTunnel(4041);
        String redirectUri = tunnel.url();
        OAuthClient client = new OAuthClient(clientId, clientSecret, port, redirectUri, 10);

        /*for (int i = 0; i < 51; i++) {
            client.getChatChannels().createChannel("A Test Channel", 1, new ArrayList<>());
            System.out.println(i);
        }*/

        System.out.println(new Gson().toJson(client.getChatChannels().listMembers("109ab13498c64fd5911a42be1076ea6b", null)));

        tunnel.close();
    }
}
