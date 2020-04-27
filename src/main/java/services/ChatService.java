package services;

import com.google.gson.Gson;
import components.ChatChannelComponent;
import components.ChatMessageComponent;
import exceptions.InvalidComponentException;
import interfaces.MessageInterface;
import models.*;
import util.DateUtil;

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

    public List<Message> history(String channelName) throws InvalidComponentException {
        List<Message> messageHistory = new ArrayList<>();
        Channel channel = findByChannelName(channelName);
        if (channel != null) {
            getMessages("me", channel.getId(), 1, null, messageHistory);
            Collections.sort(messageHistory, Comparator.comparing(Message::getDateTime));
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

        if (channelCollection.getTotalRecords() != 0) {
            channels.addAll(channelCollection.getChannels());

            if (!channelCollection.getNextPageToken().isEmpty()) {
                getChannels(channelCollection.getNextPageToken(), channels);
            }
        }
    }

    private void getMessages(String memberId, String channelId, int recipientType, String nextPageToken, List<Message> messages) throws InvalidComponentException {
        Map<String, Object> params = new HashMap<>();
        /*Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, -5);
        calendar.add(Calendar.DAY_OF_MONTH, -29);
        params.put("date", DateUtil.dateToString(calendar.getTime()));*/
        params.put("page_size", 50);

        if (nextPageToken != null) {
            params.put("next_page_token", nextPageToken);
        }

        MessageCollection collection = chatMessages.listMessages(memberId, channelId, recipientType, params);
        System.out.println(new Gson().toJson(collection));

        if (collection.getMessages() != null) {
            messages.addAll(collection.getMessages());

            if (!collection.getNextPageToken().isEmpty()) {
                getMessages(memberId, channelId, recipientType, collection.getNextPageToken(), messages);
            }
        }
    }


}
