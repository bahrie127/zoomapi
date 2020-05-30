package components;

import entities.MessageEntity;
import exceptions.InvalidComponentException;
import exceptions.InvalidEntityException;
import models.Message;
import models.MessageCollection;
import models.SentMessage;
import models.User;
import repositories.MessageRepository;
import util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CachedChatMessageComponent {

    private ChatMessageComponent chatMessageComponent = new ChatMessageComponent();
    private UserComponent userComponent = new UserComponent();
    private MessageRepository messageRepository;
    private String sender;
    private static final long CACHE_INVALIDATION_TIME = 20;

    public CachedChatMessageComponent() throws InvalidEntityException, InvalidComponentException {
        this.messageRepository = new MessageRepository();

        // retrieves user email for sender information when sending or updating a message
        User currentUser = this.userComponent.get("me", null);
        this.sender = currentUser.getEmail();
    }

    //TODO: save messages that are fetched from Zoom
    public MessageCollection listMessages(String userId, String to, int recipientType, Map<String, Object> params) throws InvalidComponentException {
        /*LocalDate date;

        if (params.containsKey("date")) {
            date = (LocalDate) params.get("date");
        } else {
            date = LocalDate.now(ZoneOffset.UTC);
        }

        List<MessageEntity> messageEntities = messageRepository.getByDate(date);
        if (messageEntities.size() > 0) {
            MessageEntity latestEntity = messageEntities.get(0);
            long timeDifference = DateUtil.minutesBetween(LocalDateTime.now(ZoneOffset.UTC), latestEntity.getCachedDate());

            if (timeDifference <= CACHE_INVALIDATION_TIME) {
                return formColletion(messageEntities);
            }
        }*/

        return chatMessageComponent.listMessages(userId, to, recipientType, params);
    }

    //TODO: for now only works for channels, make it work for contacts too
    //TODO: get correct sender
    public SentMessage postMessage(String message, String to, int recipientType) throws InvalidComponentException {
        SentMessage sentMessage = chatMessageComponent.postMessage(message, to, recipientType);

        LocalDateTime date = LocalDateTime.now(ZoneOffset.UTC);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(sentMessage.getId());
        messageEntity.setMessage(message);
        messageEntity.setSender(this.sender);
        messageEntity.setDateTime(date);
        messageEntity.setTimestamp(date.toInstant(ZoneOffset.UTC).toEpochMilli());
        messageEntity.setChannelId(to);
        messageEntity.setCachedDate(LocalDateTime.now(ZoneOffset.UTC));

        messageRepository.save(messageEntity);

        return sentMessage;
    }

    public void putMessage(String messageId, String message, String to, int recipientType) throws InvalidComponentException {
        chatMessageComponent.putMessage(messageId, message, to, recipientType);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(messageId);
        messageEntity.setMessage(message);
        messageEntity.setSender(this.sender);

        messageEntity.setCachedDate(LocalDateTime.now(ZoneOffset.UTC));

        messageRepository.save(messageEntity);
    }
    public void deleteMessage(String messageId, String to, int recipientType) throws InvalidComponentException {
        chatMessageComponent.deleteMessage(messageId, to, recipientType);
        messageRepository.remove(messageId);
    }

    private MessageCollection formColletion(List<MessageEntity> messages) {
        MessageCollection collection = new MessageCollection();

        collection.setDate(LocalDateTime.now().toString());
        collection.setMessages(entitiesToModels(messages));
        collection.setPageSize(1);
        collection.setTotalRecords(messages.size());

        return collection;
    }

    private List<Message> entitiesToModels(List<MessageEntity> entities) {
        List<Message> models = new ArrayList<>();

        for (MessageEntity entity : entities) {
            Message model = new Message();

            model.setId(entity.getId());
            model.setMessage(entity.getMessage());
            model.setDateTime(entity.getDateTime());
            model.setSender(entity.getSender());
            model.setTimestamp(entity.getTimestamp());

            models.add(model);
        }

        return models;
    }

}
