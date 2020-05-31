package repositories;

import entities.MessageEntity;
import exceptions.InvalidEntityException;
import util.DateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MessageRepository extends Repository<MessageEntity, String> {
    public MessageRepository() throws InvalidEntityException {
        super(MessageEntity.class);
    }

    public List<MessageEntity> getByDate(LocalDate localDate) {
        String dateMin = DateUtil.localDateTimeToString(localDate.atTime(LocalTime.MIN));
        String dateMax = DateUtil.localDateTimeToString(localDate.atTime(LocalTime.MAX));
        String where = "date_time BETWEEN '" + dateMin + "' AND '" + dateMax + "' ORDER BY date_time asc;";
        return get(where);
    }
}
