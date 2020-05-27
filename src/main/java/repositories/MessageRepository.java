package repositories;

import exceptions.InvalidEntityException;
import models.Message;

public class MessageRepository extends Repository<Message, String> {
    public MessageRepository() throws InvalidEntityException {
        super(Message.class);
    }
}
