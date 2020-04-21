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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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

//        FIXME: SOMETHING NOT RIGHT WHEN TRYING TO SEND A MESSAGE

        HttpResponse channelListResponse = client.getChatChannels().listChannels( null);
        String[] channelList = gson.toJson(channelListResponse.body()).split("\\\\");
        for(String channels : channelList) {
            System.out.println(channels);
        }

        for(String channels : channelList) {
            if(channels.contains("test")) {
                String channelName = channels.substring(1,channels.length());
                System.out.println(channelName);
                boolean flag = true;
                while(flag) {
                    Scanner scan = new Scanner(System.in);
                    System.out.println("Enter Something: ");
                    String mess = scan.next();
                    if(mess.equals("stop"))
                        break;
                    HashMap<String, Object> message = new HashMap<>();
                    message.put("message",mess);
                    System.out.println((client.getChatMessages().putMessage("109ab13498c64fd5911a42be1076ea6b", null, message)));
                }
            }
        }
        tunnel.close();
    }
}
/*
print(json.loads(client.meeting.list(user_id="me").content))
client.chat_channels.list()
channels = json.loads(client.chat_channels.list().content)["channels"]
print(channels)
for c in channels:
    print(c)
    if "test" in c.values():
        print("Found channel test", c["id"])
        cid = to_channel=c["id"]
stop = False
while not stop:
    message = input("Enter message: ")
    print(client.chat_messages.post(to_channel=cid, message=message))
    if message == "stop":
        stop = True
 */