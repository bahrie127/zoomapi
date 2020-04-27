package services;

import com.google.gson.Gson;
import components.ChatChannelComponent;
import components.ChatMessageComponent;
import exceptions.InvalidComponentException;
import interfaces.MessageInterface;
import models.*;

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

        if (currentChannel != null) {
            chatMessages.postMessage(message, currentChannel.getId(), 1);
        }
    }

    private Channel findByChannelName(String channelName) throws InvalidComponentException {
        List<Channel> channels = new ArrayList<>();
        getChannels(null, channels);

        for(Channel channel: channels) {
            if(channel.getName().equals(channelName)) {
                return channel;
            }
        }
        return null;
    }

    private void getChannels(String nextPageToken, List<Channel> channels) throws InvalidComponentException {
        Map<String, Object> params = new HashMap<>();
        params.put("page_size", 50);

        if (nextPageToken != null) {
            params.put("next_page_token", nextPageToken);
        }

        ChannelCollection channelCollection = chatChannels.listChannels(params);;
        channels.addAll(channelCollection.getChannels());

        if (!channelCollection.getNextPageToken().isEmpty()) {
            getChannels(channelCollection.getNextPageToken(), channels);
        }
    }

    public List<Message> history(String channelName) throws InvalidComponentException {
        List<Message> messageHistory = new ArrayList<>();
        Channel channel = findByChannelName(channelName);
        if (channel != null) {
            ChannelMemberCollection channelMembers = chatChannels.listMembers(channel.getId(), null);

            if (channelMembers != null) {
                Map<String, Object> params = new HashMap<>();
                params.put("date", "2010-01-01");
                params.put("page_size", 50);

                for (ChannelMember member : channelMembers.getMembers()) {
                    MessageCollection memberMessages = chatMessages.listMessages(member.getId(), channel.getId(), 1, params);
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
