package repositories;

import models.Message;

public class MessageRepository extends Repository<Message> {
    public MessageRepository() {
        super("messages", Message.class);
    }
}
