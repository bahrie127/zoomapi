package repositories;

import entities.MessageEntity;
import exceptions.InvalidEntityException;
import models.Message;

public class MessageRepository extends Repository<MessageEntity, String> {
    public MessageRepository() throws InvalidEntityException {
        super(MessageEntity.class);
    }
}
