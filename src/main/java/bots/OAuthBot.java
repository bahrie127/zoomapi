package bots;

import clients.OAuthClient;
import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import exceptions.InvalidEntityException;
import models.*;
import models.Channel;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.ini4j.Wini;
import xyz.dmanchon.ngrok.client.NgrokTunnel;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OAuthBot {

    private static final String SECTION_NAME = "OAuth";

    public static void main(String[] args) throws IOException, UnirestException, OAuthSystemException, InterruptedException, OAuthProblemException, InvalidEntityException, InvalidComponentException, InvalidArgumentException {
        Wini ini = new Wini(new File(OAuthBot.class.getClassLoader().getResource("bot.ini").getFile()));

        String clientId = ini.get(SECTION_NAME, "client_id", String.class);
        String clientSecret = ini.get(SECTION_NAME, "client_secret", String.class);
        Integer port = ini.get(SECTION_NAME, "port", Integer.class);

        NgrokTunnel tunnel = new NgrokTunnel(4041);
        String redirectUri = tunnel.url();
        OAuthClient client = new OAuthClient(clientId, clientSecret, port, redirectUri, 10);

        tunnel.close();

        Gson gson = new Gson();

        /*client.getChatListener().onNewMessage("test", (message) -> System.out.println("Message received: " + gson.toJson(message)));
        client.getChatListener().onMessageUpdate("test", (message) -> System.out.println("Message updated: " + gson.toJson(message)));
        client.getChatListener().onNewMember("test", (member) -> System.out.println("New member: " + gson.toJson(member)));*/

        client.getChatChannels().listChannels(null);
        System.out.println(gson.toJson(client.getChatChannels().listChannels(null)));

        Channel channel = client.getChatChannels().createChannel("PUBLIC cached channel", 1, new ArrayList<String>(Arrays.asList("rafael.bellotti@gmail.com")));
        client.getChatChannels().updateChannel(channel.getId(), "updated cached channel");


        //client.getChatMessages().postMessage("hello", "109ab13498c64fd5911a42be1076ea6b", 1);

        //client.getChatMessages().deleteMessage(message.getId(), "97601359-20b6-4b64-a445-177b3231b1c0", 1);

        //client.getChatMessages().putMessage("d83d4f47-1149-4f3b-a38a-da072e8b248a", "nope2", "97601359-20b6-4b64-a445-177b3231b1c0", 1);
        LocalDate date = LocalDate.of(2020, 05, 19);
        Map<String, Object> params = new HashMap<>();
        params.put("date", date.toString());

        System.out.println(gson.toJson(client.getChatMessages().listMessages("me", "109ab13498c64fd5911a42be1076ea6b", 1, params)));

        //client.getChatChannels().createChannel("cached channel", 1, new ArrayList<String>(Arrays.asList("nicalgrant@gmail.com")));
        //System.out.println(gson.toJson(client.getChatChannels().getChannel("97601359-20b6-4b64-a445-177b3231b1c0")));
    }
}

// Test channel id: 109ab13498c64fd5911a42be1076ea6b