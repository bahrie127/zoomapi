package repositories;

import entities.MessageEntity;
import exceptions.InvalidEntityException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MessageRepository extends Repository<MessageEntity, String> {
    public MessageRepository() throws InvalidEntityException {
        super(MessageEntity.class);
    }

    public List<MessageEntity> getByDate(LocalDate localDate) {
        String where = "where date_time BETWEEN '" + localDate.atTime(LocalTime.MIN).toString() + "' AND '" + localDate.atTime(LocalTime.MAX).toString() + "' ORDER BY date_time asc;";
        return get(where);
    }
}
