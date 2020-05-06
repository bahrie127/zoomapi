package bots;

import clients.OAuthClient;
import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import models.Message;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.ini4j.Wini;
import xyz.dmanchon.ngrok.client.NgrokTunnel;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

public class OAuthBot {

    private static final String SECTION_NAME = "OAuth";

    public static void main(String[] args) throws IOException, UnirestException, OAuthSystemException, InterruptedException, OAuthProblemException, InvalidComponentException, InvalidArgumentException {
        Wini ini = new Wini(new File(OAuthBot.class.getClassLoader().getResource("bot.ini").getFile()));

        String clientId = ini.get(SECTION_NAME, "client_id", String.class);
        String clientSecret = ini.get(SECTION_NAME, "client_secret", String.class);
        Integer port = ini.get(SECTION_NAME, "port", Integer.class);

        NgrokTunnel tunnel = new NgrokTunnel(4041);
        String redirectUri = tunnel.url();
        OAuthClient client = new OAuthClient(clientId, clientSecret, port, redirectUri, 10);

        tunnel.close();

        Gson gson = new Gson();
        client.getChatListener().onNewMessage("test", (message) -> System.out.println(gson.toJson(message)));
        //client.getChatListener().onMessageUpdate("test", (message) -> System.out.println(gson.toJson(message)));
        //client.getChatListener().onNewMember("New Channel", (member) -> System.out.println(gson.toJson(member)));
    }
}