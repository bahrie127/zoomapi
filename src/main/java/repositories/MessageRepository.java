package repositories;

import entities.MessageEntity;
import exceptions.InvalidEntityException;
import util.DateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void removeByChannelId(String channelId) {
        Map<String, Object> params = new HashMap<>();

        params.put("channel_id", channelId);

        removeByCondition(params);
    }
}
