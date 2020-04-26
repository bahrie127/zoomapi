package services;

import components.ChatChannelComponent;
import components.ChatMessageComponent;
import exceptions.InvalidArgumentException;
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

    public void sendMessage(String channel, String message) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {

        Channel currentChannel = findByChannelName(channel);
        chatMessages.postMessage(message, currentChannel.getId(), 1);
    }

    private Channel findByChannelName(String channelName) throws InterruptedException, IOException, URISyntaxException {

        ChannelPage channelPage = chatChannels.listChannels(null);;

        for(Channel channel: channelPage.getChannels()) {
            if(channel.getName().equals(channelName)) {
                return channel;
            }
        }
        return null;
    }
}
