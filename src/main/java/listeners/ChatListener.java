package listeners;

import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import interfaces.MemberCallbackInterface;
import interfaces.MessageCallbackInterface;
import models.ChannelMember;
import models.Message;
import services.ChatService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ChatListener extends Listener {

    private ChatService chatService;

    public ChatListener() {
        super();
        this.chatService = new ChatService();
    }

    public void onNewMessage(String channelName, MessageCallbackInterface callback) {

    }

    public void onMessageUpdate(String channelName, MessageCallbackInterface callback) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        List<Message> messages = new ArrayList<>();

        registerEvent(() -> {
            LocalDate toDate = LocalDate.now();
            LocalDate fromDate = toDate.minusDays(5);
            try {
                List<Message> retrievedMessages = this.chatService.history(channelName, fromDate, toDate);

                for (Message retrievedMessage : retrievedMessages) {
                    int matchedMessageId = findMessageById(messages, retrievedMessage.getId());
                    if (matchedMessageId == -1) { // if message doesnt exist locally, adds it to the list
                        messages.add(retrievedMessage);
                    } else if (!messages.get(matchedMessageId).getMessage().equals(retrievedMessage.getMessage())) { // if content of the message was changed
                        callback.call(retrievedMessage);
                        messages.set(matchedMessageId, retrievedMessage);
                    }
                }

            } catch (InvalidComponentException | InvalidArgumentException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void onNewMember(String channelName, MemberCallbackInterface callback) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        List<ChannelMember> members = new ArrayList<>();
        registerEvent(() -> {
            try {
                List<ChannelMember> retrievedMembers = chatService.members(channelName);

                if (!members.isEmpty()) {
                    for (ChannelMember retrievedMember : retrievedMembers) {
                        if (!members.stream().filter(o -> o.getId().equals(retrievedMember.getId())).findFirst().isPresent()) {
                            callback.call(retrievedMember);
                        }
                    }
                }

                // this guarantees that if members are removed, if added again it will generate an event for that member
                members.clear();
                members.addAll(retrievedMembers);

            } catch (InvalidComponentException e) {
                e.printStackTrace();
            }
        });
    }

    private int findMessageById(List<Message> messages, String messageId) {
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getId().equals(messageId)) {
                return i;
            }
        }

        return -1;
    }

}
