package services;

import components.ChatChannelComponent;
import components.ChatMessageComponent;
import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import interfaces.MessageInterface;
import models.*;

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

    public void sendMessage(String channelName, String message) throws InvalidComponentException {
        Channel currentChannel = findByChannelName(channelName);
        chatMessages.postMessage(message, currentChannel.getId(), 1);
    }

    private Channel findByChannelName(String channelName) throws InvalidComponentException {
        ChannelPage channelPage = chatChannels.listChannels(null);;

        for(Channel channel: channelPage.getChannels()) {
            if(channel.getName().equals(channelName)) {
                return channel;
            }
        }
        return null;
    }

    public List<Message> history(String channelName) throws InvalidComponentException {
        List<Message> messageHistory = new ArrayList<>();
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

        return messageHistory;
    }

    public List<Message> search(String channelName, MessageInterface condition) throws InvalidComponentException {
        List<Message> messages = new ArrayList<>();
        List<Message> messageHistory = history(channelName);

        for (Message message : messageHistory) {
            if (condition.call(message)) {
                messages.add(message);
            }
        }

        return messages;
    }

}
