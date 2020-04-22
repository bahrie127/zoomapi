package bots;

import clients.OAuthClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import exceptions.InvalidArgumentException;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.ini4j.Wini;
import xyz.dmanchon.ngrok.client.NgrokTunnel;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class OAuthBot {

    private static final String SECTION_NAME = "OAuth";

    public static void main(String[] args) throws IOException, UnirestException, OAuthProblemException, OAuthSystemException, URISyntaxException, InterruptedException, InvalidArgumentException {
        Wini ini = new Wini(new File(OAuthBot.class.getClassLoader().getResource("bot.ini").getFile()));

        String clientId = ini.get(SECTION_NAME, "client_id", String.class);
        String clientSecret = ini.get(SECTION_NAME, "client_secret", String.class);
        Integer port = ini.get(SECTION_NAME, "port", Integer.class);

        NgrokTunnel tunnel = new NgrokTunnel(4041);
        String redirectUri = tunnel.url();
        OAuthClient client = new OAuthClient(clientId, clientSecret, port, redirectUri, 5);

        tunnel.close();

        Gson gson = new Gson();

        System.out.println(gson.toJson(client.getUser().get("me", null)));
        System.out.println("----------");

        // Testing chat channels component
        /*JsonObject createChannelResponse = client.getChatChannels().createChannel("testing", 1, new ArrayList<>(Arrays.asList("nicalgrant@gmail.com")));
        System.out.println(gson.toJson(createChannelResponse));
        String channelId = createChannelResponse.get("id").getAsString();
        System.out.println("----------");

        System.out.println(gson.toJson(client.getChatChannels().getChannel(channelId)));
        System.out.println("----------");

        System.out.println(gson.toJson(client.getChatChannels().listChannels(null)));
        System.out.println("----------");

        System.out.println(gson.toJson(client.getChatChannels().listMembers(channelId, null)));
        System.out.println("----------");

        //FIXME: this is not working for some reason
        System.out.println(gson.toJson(client.getChatChannels().updateChannel(channelId, "Testing")));
        System.out.println("----------");

        //FIXME: this is not working for some reason
        System.out.println(gson.toJson(client.getChatChannels().removeMember("1d02647a-5235-40ce-accd-90ed9e61905a", "4esghsytteeabljsq8vdrw")));
        System.out.println("----------");

        //FIXME: this is not working for some reason
        System.out.println(gson.toJson(client.getChatChannels().inviteMembers(channelId, new ArrayList<>(Arrays.asList("nicalgrant@gmail.com")))));
        System.out.println("----------");

        //FIXME: this is not working for some reason
        System.out.println(gson.toJson(client.getChatChannels().joinChannel("publicChannelId")));
        System.out.println("----------");

        //FIXME: this is not working for some reason
        System.out.println(gson.toJson(client.getChatChannels().leaveChannel("publicChannelId")));
        System.out.println("----------");

        System.out.println(gson.toJson(client.getChatChannels().deleteChannel(channelId)));
        System.out.println("----------");*/

        //Testing chat messages
        JsonObject messageResponse = client.getChatMessages().postMessage("Hello!", "rafael.bellotti@gmail.com", 0);
        System.out.println(gson.toJson(messageResponse));
        String messageId = messageResponse.get("id").getAsString();
        System.out.println("----------");

        System.out.println(gson.toJson(client.getChatMessages().listMessages("me", "rafael.bellotti@gmail.com", 0, null)));
        System.out.println("----------");

        client.getChatMessages().putMessage(messageId, "Changed message", "rafael.bellotti@gmail.com", 0);

        client.getChatMessages().deleteMessage(messageId, "rafael.bellotti@gmail.com", 0);
    }
}