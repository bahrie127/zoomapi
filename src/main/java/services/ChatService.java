package services;

import com.google.gson.Gson;
import components.ChatChannelComponent;
import components.ChatMessageComponent;
import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import interfaces.MessageInterface;
import models.*;
import util.DateUtil;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

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

    public List<Message> history(String channelName, LocalDate fromDate, LocalDate toDate) throws InvalidComponentException, InvalidArgumentException {
        if (fromDate.isAfter(toDate)) {
            throw new InvalidArgumentException("fromDate must be before toDate.");
        }

        if (DAYS.between(fromDate, toDate) > 5) {
            throw new InvalidArgumentException("Date difference can't be greater than 5.");
        }

        List<Message> messageHistory = new ArrayList<>();
        Channel channel = findByChannelName(channelName);
        if (channel != null) {
            for (LocalDate currentDate = fromDate; !currentDate.isAfter(toDate); currentDate = currentDate.plusDays(1)) {
                getMessagesByDate("me", channel.getId(), 1, currentDate, null, messageHistory);
            }
            Collections.sort(messageHistory, Comparator.comparing(Message::getDateTime));
        }

        return messageHistory;
    }

    public List<Message> search(String channelName, LocalDate fromDate, LocalDate toDate, MessageInterface condition) throws InvalidComponentException, InvalidArgumentException {
        List<Message> messages = new ArrayList<>();
        List<Message> messageHistory = history(channelName, fromDate, toDate);

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

    private void getMessagesByDate(String memberId, String channelId, int recipientType, LocalDate date,
                             String nextPageToken, List<Message> messages) throws InvalidComponentException {

        Map<String, Object> params = new HashMap<>();

        params.put("date", date.toString());
        params.put("page_size", 50);

        if (nextPageToken != null) {
            params.put("next_page_token", nextPageToken);
        }

        MessageCollection collection = chatMessages.listMessages(memberId, channelId, recipientType, params);

        if (collection.getMessages() != null) {
            messages.addAll(collection.getMessages());

            if (!collection.getNextPageToken().isEmpty()) {
                getMessagesByDate(memberId, channelId, recipientType, date, collection.getNextPageToken(), messages);
            }
        }
    }


}
