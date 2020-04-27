package bots;

import clients.OAuthClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import models.Channel;
import models.SentMessage;
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

    public static void main(String[] args) throws IOException, UnirestException, OAuthSystemException, InterruptedException, OAuthProblemException, InvalidComponentException {
        Wini ini = new Wini(new File(OAuthBot.class.getClassLoader().getResource("bot.ini").getFile()));

        String clientId = ini.get(SECTION_NAME, "client_id", String.class);
        String clientSecret = ini.get(SECTION_NAME, "client_secret", String.class);
        Integer port = ini.get(SECTION_NAME, "port", Integer.class);

        NgrokTunnel tunnel = new NgrokTunnel(4041);
        String redirectUri = tunnel.url();
        OAuthClient client = new OAuthClient(clientId, clientSecret, port, redirectUri, 10);

        tunnel.close();
        Gson gson = new Gson();

        System.out.println("User");
        System.out.println(gson.toJson(client.getUser().get("me", null)));
        System.out.println("----------");

        // Testing chat channels component
        System.out.println("Creating channel");
        Channel createChannelResponse = client.getChatChannels().createChannel("A Test Channel", 1, new ArrayList<>(Arrays.asList("nicalgrant@gmail.com")));
        System.out.println(gson.toJson(createChannelResponse));
        String channelId = createChannelResponse.getId();
        System.out.println("----------");

        System.out.println("Getting channel information");
        System.out.println(gson.toJson(client.getChatChannels().getChannel(channelId)));
        System.out.println("----------");

        System.out.println("Listing channels");
        System.out.println(gson.toJson(client.getChatChannels().listChannels(null)));
        System.out.println("----------");

        System.out.println("Listing channel members");
        System.out.println(gson.toJson(client.getChatChannels().listMembers(channelId, null)));
        System.out.println("----------");

        System.out.println("Removing member from channel");
        client.getChatChannels().removeMember(channelId, "4esghsytteeabljsq8vdrw");

        System.out.println("Updating channel");
        client.getChatChannels().updateChannel(channelId, "My New Channel");

        System.out.println("Inviting member to channel");
        System.out.println(gson.toJson(client.getChatChannels().inviteMembers(channelId, new ArrayList<>(Arrays.asList("nicalgrant@gmail.com")))));
        System.out.println("----------");

        System.out.println("Creating channel for deletion");
        Channel createChannelToBeDeletedResponse = client.getChatChannels().createChannel("To Be Deleted", 1, new ArrayList<>());
        System.out.println(gson.toJson(createChannelToBeDeletedResponse));
        String deletedChannelId = createChannelToBeDeletedResponse.getId();
        System.out.println("----------");

        System.out.println("Leaving channel");
        client.getChatChannels().leaveChannel(channelId);

        System.out.println("Joining channel");
        System.out.println(gson.toJson(client.getChatChannels().joinChannel(channelId)));
        System.out.println("----------");

        System.out.println("Deleting channel");
        client.getChatChannels().deleteChannel(deletedChannelId);

        //Testing chat messages
        System.out.println("Sending message");
        SentMessage messageResponse = client.getChatMessages().postMessage("Hello!", "rafael.bellotti@gmail.com", 0);
        System.out.println(gson.toJson(messageResponse));
        String messageId = messageResponse.getId();
        System.out.println("----------");

        System.out.println("Listing messages");
        System.out.println(gson.toJson(client.getChatMessages().listMessages("me", "rafael.bellotti@gmail.com", 0, null)));
        System.out.println("----------");

        System.out.println("Updated message");
        client.getChatMessages().putMessage(messageId, "Changed message", "rafael.bellotti@gmail.com", 0);

        System.out.println("Deleted message");
        client.getChatMessages().deleteMessage(messageId, "rafael.bellotti@gmail.com", 0);
    }
}