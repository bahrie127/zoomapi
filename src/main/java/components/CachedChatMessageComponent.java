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
import java.util.Optional;

public class CachedChatMessageComponent {

    private ChatMessageComponent chatMessageComponent = new ChatMessageComponent();
    private UserComponent userComponent = new UserComponent();
    private MessageRepository messageRepository;
    private String sender;
    private static final long CACHE_INVALIDATION_TIME = 20;

    public CachedChatMessageComponent() throws InvalidEntityException {
        this.messageRepository = new MessageRepository();
    }

    //TODO: save messages that are fetched from Zoom
    public MessageCollection listMessages(String userId, String to, int recipientType, Map<String, Object> params) throws InvalidComponentException {
        LocalDate date;

        if (params != null && params.containsKey("date")) {
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
        }

        return chatMessageComponent.listMessages(userId, to, recipientType, params);
    }

    //TODO: for now only works for channels, make it work for contacts too
    //TODO: get correct sender
    public SentMessage postMessage(String message, String to, int recipientType) throws InvalidComponentException {
        getUser();

        SentMessage sentMessage = chatMessageComponent.postMessage(message, to, recipientType);
        messageRepository.save(createEntity(sentMessage.getId(), message, to));

        return sentMessage;
    }

    //TODO: check timestamp if it is correct
    public void putMessage(String messageId, String message, String to, int recipientType) throws InvalidComponentException {
        getUser();

        chatMessageComponent.putMessage(messageId, message, to, recipientType);

        Optional<MessageEntity> optionalMessageEntity = messageRepository.findById(messageId);

        if (optionalMessageEntity.isPresent()) {
            MessageEntity messageEntity = optionalMessageEntity.get();

            LocalDateTime date = LocalDateTime.now(ZoneOffset.UTC);
            messageEntity.setMessage(message);
            messageEntity.setCachedDate(date);
            messageRepository.save(messageEntity);
        }

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
            model.setDateTime(DateUtil.localDateTimeToDate(entity.getDateTime()));
            model.setSender(entity.getSender());
            model.setTimestamp(entity.getTimestamp());

            models.add(model);
        }

        return models;
    }

    private void getUser() throws InvalidComponentException {
        if (this.sender == null) {
            User currentUser = this.userComponent.get("me", null);
            this.sender = currentUser.getEmail();
        }
    }

    private MessageEntity createEntity(String id, String message, String to) {
        MessageEntity messageEntity = new MessageEntity();

        LocalDateTime date = LocalDateTime.now(ZoneOffset.UTC);

        messageEntity.setId(id);
        messageEntity.setMessage(message);
        messageEntity.setSender(this.sender);
        messageEntity.setDateTime(date);
        messageEntity.setTimestamp(date.toInstant(ZoneOffset.UTC).toEpochMilli());
        messageEntity.setChannelId(to);
        messageEntity.setCachedDate(date);

        return messageEntity;
    }

}
