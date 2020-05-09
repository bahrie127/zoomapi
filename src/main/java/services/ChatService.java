package services;

import components.ChatChannelComponent;
import components.ChatMessageComponent;
import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import models.*;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import static java.time.temporal.ChronoUnit.DAYS;

public class ChatService {

    private ChatChannelComponent chatChannel;
    private ChatMessageComponent chatMessage;

    public ChatService() {
        this.chatChannel = new ChatChannelComponent();
        this.chatMessage = new ChatMessageComponent();
    }

    public void sendMessage(String channelName, String message) throws InvalidComponentException, InvalidArgumentException {
        Channel currentChannel = findByChannelName(channelName, null);
        chatMessage.postMessage(message, currentChannel.getId(), 1);
    }

    public List<Message> history(String channelName, LocalDate fromDate, LocalDate toDate) throws InvalidComponentException, InvalidArgumentException {
        if (fromDate.isAfter(toDate)) {
            throw new InvalidArgumentException("fromDate must be before toDate.");
        }

        if (DAYS.between(fromDate, toDate) > 5) {
            throw new InvalidArgumentException("Date difference can't be greater than 5.");
        }

        List<Message> messageHistory = new ArrayList<>();
        Channel channel = findByChannelName(channelName, null);
        if (channel != null) {
            for (LocalDate currentDate = fromDate; !currentDate.isAfter(toDate); currentDate = currentDate.plusDays(1)) {
                getMessagesByDate("me", channel.getId(), 1, currentDate, null, messageHistory);
            }
            Collections.sort(messageHistory, Comparator.comparing(Message::getDateTime));
        }

        return messageHistory;
    }

    public List<Message> search(String channelName, LocalDate fromDate, LocalDate toDate, Predicate condition) throws InvalidComponentException, InvalidArgumentException {
        List<Message> messages = new ArrayList<>();
        List<Message> messageHistory = history(channelName, fromDate, toDate);

        for (Message message : messageHistory) {
            if (condition.test(message)) {
                messages.add(message);
            }
        }

        return messages;
    }

    public List<ChannelMember> members(String channelName) throws InvalidComponentException, InvalidArgumentException {
        Channel channel = findByChannelName(channelName, null);

        List<ChannelMember> members = new ArrayList<>();
        getMembers(channel.getId(), null, members);

        return members;
    }

    private Channel findByChannelName(String channelName, String nextPageToken) throws InvalidComponentException, InvalidArgumentException {
        Map<String, Object> params = new HashMap<>();
        params.put("page_size", 50);

        if (nextPageToken != null) {
            params.put("next_page_token", nextPageToken);
        }

        ChannelCollection channelCollection = chatChannel.listChannels(params);;

        if (channelCollection.getTotalRecords() != 0) {

            for(Channel channel: channelCollection.getChannels()) {
                if(channel.getName().equals(channelName)) {
                    return channel;
                }
            }

            if (!channelCollection.getNextPageToken().isEmpty()) {
                return findByChannelName(channelName, channelCollection.getNextPageToken());
            }
        }

        throw new InvalidArgumentException("Channel doesn't exist.");
    }

    private void getMessagesByDate(String memberId, String channelId, int recipientType, LocalDate date,
                             String nextPageToken, List<Message> messages) throws InvalidComponentException {

        Map<String, Object> params = new HashMap<>();

        params.put("date", date.toString());
        params.put("page_size", 50);

        if (nextPageToken != null) {
            params.put("next_page_token", nextPageToken);
        }

        MessageCollection collection = chatMessage.listMessages(memberId, channelId, recipientType, params);

        if (collection.getMessages() != null) {
            messages.addAll(collection.getMessages());

            if (!collection.getNextPageToken().isEmpty()) {
                getMessagesByDate(memberId, channelId, recipientType, date, collection.getNextPageToken(), messages);
            }
        }
    }

    private void getMembers(String channelId, String nextPageToken, List<ChannelMember> members) throws InvalidComponentException {
        Map<String, Object> params = new HashMap<>();
        params.put("page_size", 100);

        if (nextPageToken != null) {
            params.put("next_page_token", nextPageToken);
        }

        ChannelMemberCollection collection = chatChannel.listMembers(channelId, params);

        if (collection.getMembers() != null) {
            members.addAll(collection.getMembers());

            if (!collection.getNextPageToken().isEmpty()) {
                getMembers(channelId, collection.getNextPageToken(), members);
            }
        }
    }

}
