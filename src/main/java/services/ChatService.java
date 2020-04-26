package services;

import components.ChatChannelComponent;
import components.ChatMessageComponent;
import models.Channel;
import models.ChannelPage;
import exceptions.InvalidArgumentException;
import models.ChannelMember;
import models.ChannelMemberPage;
import models.Message;
import models.MessagePage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

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
        ChannelPage channelPage = chatChannels.listChannels(null);

        for (Channel channel : channelPage.getChannels()) {
            if (channel.getName().equals(channelName)) {
                return channel;
            }
        }
        return null;
    }

    public List<Message> history(String channelName) {
        List<Message> messageHistory = new ArrayList<>();
        try {
            Channel channel = findByChannelName(channelName);
            if (channel != null) {
                ChannelMemberPage channelMembers = chatChannels.listMembers(channel.getId(), null);

                if (channelMembers != null) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("date", "2010-01-01");
                    params.put("page_size", 50);

                    for (ChannelMember member : channelMembers.getMembers()) {
                        MessagePage memberMessages = chatMessages.listMessages(member.getId(), channel.getId(), 1, params);
                        messageHistory.addAll(memberMessages.getMessages());
                    }
                }

                Collections.sort(messageHistory, (Message message1, Message message2) -> message1.getDateTime().compareTo(message1.getDateTime()));
            }

        } catch(InterruptedException | IOException | URISyntaxException | InvalidArgumentException exception) { }

        return messageHistory;
    }
}
