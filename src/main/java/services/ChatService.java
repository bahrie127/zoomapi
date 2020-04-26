package services;

import components.ChatChannelComponent;
import components.ChatMessageComponent;
import models.Channel;
import models.ChannelPage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ChatService {

    private ChatChannelComponent chatChannels;
    private ChatMessageComponent chatMessages;

    public ChatService() {
        this.chatChannels = new ChatChannelComponent();
        this.chatMessages = new ChatMessageComponent();
    }

    public void sendMessage(String channel, String message) {

    }

    private Channel findByChannelName(String channelName) throws InterruptedException, IOException, URISyntaxException {

        List<String> channelObj = new ArrayList<>();
        ChannelPage channelPage = chatChannels.listChannels(null);;

        for(Channel channel: channelPage.getChannels()) {
            if(channel.getName().equals(channelName)) {
                return channel;
            }
        }
        return null;
    }
}
