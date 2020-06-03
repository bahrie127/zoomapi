package bots;

import clients.OAuthClient;
import com.google.gson.Gson;
import models.Channel;
import models.SentMessage;
import org.ini4j.Wini;
import xyz.dmanchon.ngrok.client.NgrokTunnel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OAuthBot {

    private static final String SECTION_NAME = "OAuth";

    public static void main(String[] args) {
        OAuthClient client = null;
        try {
            Wini ini = new Wini(new File(OAuthBot.class.getClassLoader().getResource("bot.ini").getFile()));

            String clientId = ini.get(SECTION_NAME, "client_id", String.class);
            String clientSecret = ini.get(SECTION_NAME, "client_secret", String.class);
            Integer port = ini.get(SECTION_NAME, "port", Integer.class);

            NgrokTunnel tunnel = new NgrokTunnel(4041);
            String redirectUri = tunnel.url();
            client = new OAuthClient(clientId, clientSecret, port, redirectUri, 10);

            tunnel.close();

            Gson gson = new Gson();
            Map<String, Object> params = new HashMap<>();
            params.put("page_size", 100);

            //Messages test
            SentMessage message = client.getChatMessages().postMessage("pool2", "109ab13498c64fd5911a42be1076ea6b", 1);
            System.out.println(gson.toJson(client.getChatMessages().listMessages("me", "109ab13498c64fd5911a42be1076ea6b", 1, params)));

            client.getChatMessages().putMessage(message.getId(), "edit", "109ab13498c64fd5911a42be1076ea6b", 1);
            client.getChatMessages().deleteMessage(message.getId(), "109ab13498c64fd5911a42be1076ea6b", 1);

            //Channels test
            Channel channel = client.getChatChannels().createChannel("testerchannel", 1, new ArrayList<>());
            System.out.println(gson.toJson(client.getChatChannels().listChannels(null)));
            System.out.println(gson.toJson(client.getChatChannels().getChannel("109ab13498c64fd5911a42be1076ea6b")));
            Channel newChannel = client.getChatChannels().createChannel("lalala", 1, new ArrayList<>());
            client.getChatChannels().updateChannel(newChannel.getId(), "lululu");
            client.getChatChannels().deleteChannel(newChannel.getId());

            //Members test
            System.out.println(gson.toJson(client.getChatChannels().listMembers("109ab13498c64fd5911a42be1076ea6b", null)));
            client.getChatChannels().inviteMembers("109ab13498c64fd5911a42be1076ea6b", new ArrayList<>(Arrays.asList("nicalgrant@gmail.com")));
            client.getChatChannels().leaveChannel(newChannel.getId());
            client.getChatChannels().joinChannel(newChannel.getId());

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}

// Test channel id: 109ab13498c64fd5911a42be1076ea6b
//TODO: work with concurrency