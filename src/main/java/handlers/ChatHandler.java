package handlers;

import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import interfaces.ChannelMemberInterface;
import interfaces.MessageInterface;
import models.Message;
import services.ChatService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatHandler {

    private ChatService chatService;
    private static final int CALL_RATE = 10;

    public ChatHandler() {
        this.chatService = new ChatService();
    }

    public void onNewMessage(String channelName, MessageInterface callback) {

    }

    public void onMessageUpdate(String channelName, MessageInterface callback) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        List<Message> messages = new ArrayList<>();
        /*executorService.scheduleAtFixedRate(() -> {
            LocalDate toDate = LocalDate.now();
            LocalDate fromDate = toDate.minusDays(5);
            try {
                List<Message> retrievedMessages = this.chatService.history(channelName, fromDate, toDate);
                if (!messages.isEmpty()) {
                    for (Message retrievedMessage : retrievedMessages) {

                    }

                }

                messages = retrievedMessages;

            } catch (InvalidComponentException | InvalidArgumentException exception) {
                exception.printStackTrace();
            }
        }, 0, CALL_RATE, TimeUnit.SECONDS);*/
    }

    public void onNewMember(String channelName, ChannelMemberInterface callback) {

    }

}
